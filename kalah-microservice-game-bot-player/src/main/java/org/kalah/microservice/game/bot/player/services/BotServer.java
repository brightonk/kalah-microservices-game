package org.kalah.microservice.game.bot.player.services;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Run as a micro-service, registering with the Discovery Server (Eureka).
 * <p>
 * Note that the configuration for this application is imported from
 * {@link BotConfiguration}. This is a deliberate separation of concerns.
 *
 * @author Brighton Kukasira
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
public class BotServer {

  private static final Logger LOGGER = Logger.getLogger(BotServer.class.getName());

  /**
   * Run the application using Spring Boot and an embedded servlet engine.
   *
   * @param args Program arguments - ignored.
   */
  public static void main(String[] args) {
    // Tell server to look for bot-server.properties or bot-server.yml
    System.setProperty("spring.config.name", "bot-server");
    SpringApplication.run(BotServer.class, args);
  }
}
