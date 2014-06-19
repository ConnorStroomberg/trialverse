(ns org.drugis.addis.rdf.trig
  (:require [clojure.string :refer [join]])
  (:require [clojure.data.json :refer [write-str]]))

(declare write-pairs)

(def _indent_ "  ")

(defn ttl-object-str
  "Resource to Turtle string for object position.
  WARNING: string literals are escaped using a JSON escaping algorithm, which may not be correct in exotic cases."
  ([prefixes resource] (ttl-object-str prefixes resource ""))
  ([prefixes resource indent]
   (let [indent+ (str indent _indent_)]
     (if (sequential? resource)
       (({:uri (fn [x] x)
          :blank (fn [x] (str "[\n" (write-pairs prefixes x indent) "\n" indent "]"))
          :coll (fn [collection]
                  (let [members (map #(ttl-object-str prefixes % indent+) collection)
                        content (str indent+ (join (str "\n" indent+) members))]
                    (str "(\n" content "\n" indent ")")))}
         (first resource))
        (second resource))
       (cond
         (instance? Boolean resource) (str resource)
         (integer? resource) (str resource)
         (float? resource) (format "%e" (double resource))
         (string? resource) (write-str resource)
         )))))

(defn ttl-str
  "Resource to Turtle string for subject and predicate positions"
  [resource]
  (if (and (sequential? resource) (= :uri (first resource)))
    (second resource)
    (throw (IllegalArgumentException. "Can only have URIs for graph, subject, and predicate positions"))))

; write pairs (predicate-object pairs)
(defn write-pairs [prefixes pairs indent]
  (let [indent+ (str indent _indent_)
        intermediate (map (fn [[k v]] (str indent+ (ttl-str k) " " (ttl-object-str prefixes v indent+))) pairs)]
    (join " ;\n" intermediate)))

; write triples regarding a single subject
(defn write-triples 
  ([prefixes triples] (write-triples prefixes triples ""))
  ([prefixes triples indent]
   (let [triple-str (write-pairs prefixes (second triples) indent)]
     (str indent (ttl-str (first triples)) "\n" triple-str " ."))))

; write a list of prefixes
(defn write-prefixes [prefixes]
  (let [write-prefix (fn [[prefix uri]] (format "@prefix %s: <%s> ." (name prefix)  uri))]
    (join "\n" (map write-prefix prefixes))))

(defn write-ttl
  [prefixes statements]
      (join "\n\n" (concat [(write-prefixes prefixes) ""] (map #(write-triples prefixes %) statements))))

(defn write-graph
  [prefixes graph]
    (join "\n\n" (concat [(str (ttl-str (first graph)) " {")] (map #(write-triples prefixes % _indent_) (second graph)) ["}"])))

(defn write-trig
  [prefixes graphs]
    (join "\n\n" (concat [(write-prefixes prefixes)] (map #(write-graph prefixes %) graphs))))

(defn rdf-uri
  ([uri] [:uri (str "<" uri ">")])
  ([prefix resource] [:uri (str (name prefix) ":" resource)]))

(defn rdf-blank
  [pairs] [:blank pairs])

(defn rdf-coll
  [resources] [:coll resources])