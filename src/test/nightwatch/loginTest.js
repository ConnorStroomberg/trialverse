var login = require('./utils/login.js');

module.exports = {
  "Trialverse login test" : function (browser) {
    login(browser, 'https://tv-test.drugis.org')
      .waitForElementVisible('body', 3000)
      .pause(2000)
      .assert.containsText('h1', 'trialverse.org')
      .end();
  }
};