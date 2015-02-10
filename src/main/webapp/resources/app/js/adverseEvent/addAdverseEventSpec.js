'use strict';
define(['angular', 'angular-mocks'], function() {
  describe('add Adverse event sparql query', function() {

    var rootScope, q, $httpBackend, http, adverseEventService, addAdverseEventQueryRaw;

    var app = angular.module('test-e2e', ['ngMockE2E', 'trialverse', 'trialverse.util', 'trialverse.study', 'trialverse.adverseEvent']);
    app.run(function($httpBackend) {
        $httpBackend.whenGET(/.*/).respond('wat');

    });

    // beforeEach(module('trialverse.util'));
    // beforeEach(module('trialverse.study'));
    // beforeEach(module('trialverse.adverseEvent'));

    beforeEach(function() {
      module('test-e2e', function($provide) {
        $provide.value('SCRATCH_RDF_STORE_URL', 'http://testmij.com');
      });
    });

    beforeEach(inject(function($q, $rootScope, $httpBackend, $http, AdverseEventService) {

      q = $q;
      rootScope = $rootScope;
      http = $http; 
      adverseEventService = AdverseEventService;
      
    }));

    describe('add adverse event query', function() {

      it('should add a adverse event to the graph', function(done) {

      	var testEvent = {
      		label: 'label',
      		measurementType: 'measurementType'
      	}

        var addItemPromise = adverseEventService.addItem(testEvent);

        rootScope.$digest();

        expect(true).toBe(false);
        done();
      });
    });

  });
});
