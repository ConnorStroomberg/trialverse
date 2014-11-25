package org.drugis.trialverse.util;

import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * Created by connor on 2/12/14.
 */
@Component
public class WebConstants {
  public final static MediaType APPLICATION_JSON_UTF8 = new MediaType(
          MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          Charset.forName("utf8"));
  public final static String APPLICATION_JSON_UTF8_VALUE = "application/json; charset=UTF-8";
  public final static String TRIPLESTORE_BASE_URI = loadSystemEnvTripleStoreBaseURI();
  public final static String TRIPLESTORE_DATA_URI = TRIPLESTORE_BASE_URI + "/current";

  private static String loadSystemEnvTripleStoreBaseURI() {
    String tripleStoreBaseURI = System.getenv("TRIPLESTORE_BASE_URI");
    if (tripleStoreBaseURI == null || tripleStoreBaseURI.isEmpty()) {
      LoggerFactory
        .getLogger(WebConstants.class)
        .error("Cannot start server, no TRIPLESTORE_BASE_URI environment variable found");
      System.exit(-1);
    }
    return tripleStoreBaseURI;
  }
}

