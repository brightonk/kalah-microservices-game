package org.kalah.microservice.game.bot.player.exceptions;

import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Allow the controller to return a 404 if a bot is not found by simply throwing
 * this exception.
 *
 * @ResponseStatus causes Spring MVC to return a 404 instead of the usual 500.
 *
 * @author Brighton Kukasira
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BotNotFoundException extends RuntimeException {

  private static final Logger LOG = Logger.getLogger(BotNotFoundException.class.getName());
  private static final long serialVersionUID = 1L;

  public BotNotFoundException(String botNumber) {
    super("No such bot: " + botNumber);
  }
}
