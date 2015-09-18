module.exports = {
  'Demo test addis' : function (browser) {
    browser
      .url('https://trialverse.org')
      .waitForElementVisible('body', 1000)
      .pause(1000)
      .assert.containsText('h1', 'Trialverse')
      .end();
  }
};