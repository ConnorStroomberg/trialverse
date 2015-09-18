var login = require('./util/login.js');

var protocol = 'https://';
var host= 'trialverse.org';
var userHash = '34c38ea0365f6155def821144e7a48e589f9fc7f57f1a5fb5f6acc3e780fdd27';
var datasetUid = '3d1b7930-3739-4c82-a52b-106b416c61e9';
var version = '41be7add-e9ce-4e70-8ae1-3ee96061c6ec';
var datasetUrl = protocol + host + '/#/users/'+ userHash +'/datasets/' + datasetUid + '/versions/' + version;

// selectors 
var datasetHeader = 'body > div.ng-scope > div > div > div.row.nav-bar-spacer > div > h1';
var studyTableStudyTitle = 'body > div.ng-scope > div > div > div:nth-child(3) > div > table > tbody > tr > td:nth-child(1) > a';
var studyNavbarLabel = 'body > div.ng-scope > div > div > div > navbar-directive > div > nav > section > ng-transclude > ul > li.name > h1 > a';
var arm1 = '#arms > div > div > category-item-directive > div.ng-scope > ul > li:nth-child(1) > span.ng-binding';
// test the dataset page
module.exports = {
  "Trialverse dataset readonly walkthough test" : function (browser) {
    login(browser, process.env.TRIALVERSE_NIGHTWATCH_URL)
      .pause(1000)
      .url(datasetUrl)
      .pause(3000)
      .assert.containsText(datasetHeader, 'Demo dataset')
      .assert.containsText(studyTableStudyTitle, 'demo study 1')
      .click(studyTableStudyTitle)
      .pause(3000)
      .assert.containsText(studyNavbarLabel, 'Demo dataset / demo study 1')
      .assert.containsText(arm1, 'Arm 1')
      .end();
  }
};
  



