package org.kalah.microservice.game.web.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Allow the controller to return a 404 if the board is not found on the server
 * by simply throwing this exception.
 *
 * @ResponseStatus causes Spring MVC to return a 404 instead of the usual 500.
 *
 * @author Brighton Kukasira
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BoardNotFoundException extends RuntimeException {

  private static final Logger LOG = LoggerFactory.getLogger(BoardNotFoundException.class);
  private static final long serialVersionUID = 1L;

  public BoardNotFoundException() {
    super("Board not available");
  }
}
