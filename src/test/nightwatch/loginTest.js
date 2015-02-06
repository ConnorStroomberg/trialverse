module.exports = {
  "Trialverse login test" : function (browser) {
    browser
      .url("https://tv-test.drugis.org")
      .waitForElementVisible('body', 1000)
      .click('button[type="submit"]')
      .pause(1000)

      .setValue('input[type=email]', ' addistestuser1@gmail.com')
      .setValue('input[type=password]', 'addistestuser123')
      .click('input[type="submit"]')
      .waitForElementVisible('.main-content', 3000)
      .pause(1000)
      .assert.containsText('h1', 'trialverse.org')
      .end();
  }
};