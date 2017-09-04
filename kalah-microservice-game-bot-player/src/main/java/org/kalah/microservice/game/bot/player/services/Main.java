package org.kalah.microservice.game.bot.player.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allow the servers to be invoked from the command-line. The jar is built with
 * this as the <code>Main-Class</code> in the jar's <code>MANIFEST.MF</code>.
 *
 * @author Brighton Kukasira
 */
public class Main {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    String serverName = "NO-VALUE";
    
    switch (args.length) {
      case 3:
      case 2:
        // Optionally set the HTTP port to listen on, overrides
        // value in the <server-name>-server.yml file
        System.setProperty("server.port", args[2]);
      // Fall through into ..
      case 1:
        serverName = args[1].toLowerCase();
        break;

      default:
        usage();
        return;
    }
    if (serverName.equals("bot")) {
      BotServer.main(args);
    } else {
      System.out.println("Unknown server type: " + serverName);
      usage();
    }
  }

  protected static void usage() {
    System.out.println("Usage: java -jar ... <server-name> [server-port]");
    System.out.println("     where server-name is 'bot' and server-port > 1024");
  }
}
