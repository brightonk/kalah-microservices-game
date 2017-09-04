package org.kalah.microservice.game.bot.player;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.kalah.microservice.game.bot.player.exceptions.BotNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RESTFul controller for accessing bot service.
 *
 * @author Brighton Kukasira
 */
@RestController
public class BotController {

  public static final Logger LOGGER = Logger.getLogger(BotController.class.getName());

  @Autowired
  public BotController() {
  }

  /**
   * Fetch an selection made by the bot when it is the bot's turn to play.
   *
   * @return
   * @throws BotNotFoundException If the number is not recognised.
   */
  @RequestMapping("/bot/selection")
  public String botSelection() {
    LOGGER.log(Level.INFO, "bot-service botSelection() invoked");
    return "";
  }
}
