/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.drugis.trialverse.security.controller;

import org.drugis.trialverse.security.Account;
import org.drugis.trialverse.security.SignInUtilService;
import org.drugis.trialverse.security.UsernameAlreadyInUseException;
import org.drugis.trialverse.security.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.inject.Inject;

@Controller
public class SignupController {

  final static Logger logger = LoggerFactory.getLogger(SignupController.class);

  private final AccountRepository accountRepository;
  private final ProviderSignInUtils providerSignInUtils;
  private final SignInUtilService signInUtilService;

  @Inject
  public SignupController(AccountRepository accountRepository,
                          ConnectionFactoryLocator connectionFactoryLocator,
                          UsersConnectionRepository connectionRepository,
                          SignInUtilService signInUtilService) {
    this.accountRepository = accountRepository;
    this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
    this.signInUtilService = signInUtilService;
  }
  @RequestMapping(value = "/signup", method = RequestMethod.GET)
  public String signupForm(WebRequest request) {
    logger.info("sign up for new account");
    Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
    if (connection != null) {
      logger.info(" C o N n e c t i o n  NOT null");
      UserProfile profile = connection.fetchUserProfile();
      logger.info("profile fetched. name: " + profile.getName() + " username: " + profile.getUsername() + " email: " + profile.getEmail());
      Account account = createAccount(profile);
      if (account != null) {
        signInUtilService.signin(connection, account.getUsername());
        providerSignInUtils.doPostSignUp(account.getUsername(), request);
        return "redirect:/";
      } else {
        logger.error("No account available ");
        return null;
      }
    } else {
      logger.error("No connection available ");
      return null;
    }
  }


  private Account createAccount(UserProfile profile) {
    try {
      logger.info("creating new account");
      accountRepository.createAccount(profile.getEmail(), profile.getFirstName(), profile.getLastName());
      logger.info("new account created");
      return accountRepository.findAccountByUsername((profile.getEmail()));
    } catch (UsernameAlreadyInUseException e) {
      logger.error("UsernameAlreadyInUseException");
      return null;
    }
  }
}
