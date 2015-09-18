module.exports = {
  'Demo test addis' : function (browser) {
    browser
      .url('https://trialverse.org')
      .waitForElementVisible('body', 3000)
      .assert.containsText('.button', 'Sign In with Google')
      .end();
  }
};