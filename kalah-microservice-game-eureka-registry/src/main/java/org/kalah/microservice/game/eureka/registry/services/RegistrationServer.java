package org.kalah.microservice.game.eureka.registry.services;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Runs a Eureka registration server.
 *
 * @author Brighton Kukasira
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistrationServer {

  private static final Logger LOG = Logger.getLogger(RegistrationServer.class.getName());

  /**
   * Run the application using Spring Boot and an embedded servlet engine.
   *
   * @param args Program arguments not required.
   */
  public static void main(String[] args) {
    // server looks for registration-server.properties or registration-server.yml
    System.setProperty("spring.config.name", "registration-server");
    SpringApplication.run(RegistrationServer.class, args);
  }
}
