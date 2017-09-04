package org.kalah.microservice.game.web.websocket.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.kalah.microservice.game.web.exceptions.UsernameNotUniqueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Brighton Kukasira <brighton.kukasira@gmail.com>
 */
public interface Players {

  public static final Logger LOG = LoggerFactory.getLogger(Players.class);
  public static final AtomicLong ID_GENERATOR = new AtomicLong(0);
  public static final ConcurrentHashMap<String, String> USERNAMES = new ConcurrentHashMap<>(0);

  /**
   * Checks the username for uniqueness and throws exception. Saves the username
   * in order to maintain a map of usernames that can be used for future checks.
   *
   * @param username
   */
  public static void checkUsernameUniqueness(String username) {
    final String existingUsername = USERNAMES.putIfAbsent(username, username);
    if (existingUsername != null) {
      throw new UsernameNotUniqueException(username);
    }
  }

  /**
   * Clean up when user disconnects.
   *
   * @param username
   */
  public static void removeUsername(String username) {
    USERNAMES.remove(username);
  }

  public static Long generateIfNullPlayerId(Player player) {
    if (player.getPlayerId() == null) {
      player.setPlayerId(ID_GENERATOR.incrementAndGet());
      return null;
    }
    return player.getPlayerId();
  }

  public static Player createPlayer(Long playerId, String username) {
    Player player = new Player();
    player.setPlayerId(playerId);
    player.setUsername(username);
    return player;
  }
}
