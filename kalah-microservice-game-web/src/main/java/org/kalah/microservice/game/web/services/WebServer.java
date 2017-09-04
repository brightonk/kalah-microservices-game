package org.kalah.microservice.game.web.services;

import org.kalah.microservice.game.web.websocket.WebSocketConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

/**
 * web-server. Works as a microservice client. Uses the Discovery Server
 * (Eureka) to find the microservice.
 *
 * @author Brighton Kukasira
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import(WebSocketConfig.class)
@ComponentScan("org.kalah.microservice.game.web.websocket")
public class WebServer {

  /**
   * URL uses the logical name of bot-service - upper or lower case, doesn't
   * matter.
   */
  public static final String BOT_SERVICE_URL = "http://BOT-SERVICE";
  private static final Logger LOG = LoggerFactory.getLogger(WebServer.class);

  /**
   * Run the application using Spring Boot and an embedded servlet engine.
   *
   * @param args Program arguments - ignored.
   */
  public static void main(String[] args) {
    // Tell server to look for web-server.properties or web-server.yml
    System.setProperty("spring.config.name", "web-server");
    SpringApplication.run(WebServer.class, args);
  }

  @Bean
  public HomeController homeController() {
    return new HomeController();
  }

  /**
   * A customized RestTemplate that has the ribbon load balancer build in. Note
   * that prior to the "Brixton"
   *
   * @return
   */
  @LoadBalanced
  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
