package org.kalah.microservice.game.bot.player;

import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home page controller.
 *
 * @author Brighton Kukasira
 */
@Controller
public class HomeController {

  private static final Logger LOG = Logger.getLogger(HomeController.class.getName());

  @RequestMapping("/")
  public String home() {
    return "index";
  }

}
