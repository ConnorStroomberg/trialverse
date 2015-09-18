module.exports = {
  'Demo test trialvese' : function (browser) {
    browser
      .url('https://trialverse.org')
      .waitForElementVisible('body', 3000)
      .assert.containsText('.button', 'Sign In with Google')
      .end();
  }
};