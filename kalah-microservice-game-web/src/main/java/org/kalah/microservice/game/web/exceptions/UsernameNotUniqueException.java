package org.kalah.microservice.game.web.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Allow the controller to return a 409 if there is a conflict with a resource
 * that exists on the server by simply throwing this exception.
 *
 * @ResponseStatus causes Spring MVC to return a 409 instead of the usual 500.
 *
 * @author Brighton Kukasira
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameNotUniqueException extends RuntimeException {

  private static final Logger LOG = LoggerFactory.getLogger(UsernameNotUniqueException.class);
  private static final long serialVersionUID = 1L;

  public UsernameNotUniqueException(String username) {
    super("Username already exists: " + username);
  }
}
