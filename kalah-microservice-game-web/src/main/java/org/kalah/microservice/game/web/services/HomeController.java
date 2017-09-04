package org.kalah.microservice.game.web.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Home page controller.
 *
 * @author Brighton Kukasira
 */
@Controller
public class HomeController {

  private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

  @RequestMapping(value = "/game", method = RequestMethod.GET)
  public String gamePage(Model model) {
    return "game";
  }

  @RequestMapping("/")
  public String home() {
    return "index";
  }
}
