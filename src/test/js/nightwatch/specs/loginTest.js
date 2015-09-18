var login = require('./util/login.js');

// test the login

module.exports = {
  "gemtc login test" : function (browser) {
    login(browser, process.env.TRIALVERSE_NIGHTWATCH_URL)
      .pause(3000)
      .assert.containsText('.row.nav-bar-spacer h1', 'Ulrika Tester')
      .end();
  }
};
