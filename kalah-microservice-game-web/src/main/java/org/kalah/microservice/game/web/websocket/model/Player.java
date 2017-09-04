package org.kalah.microservice.game.web.websocket.model;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Brighton Kukasira <brighton.kukasira@gmail.com>
 */
public class Player {

  private static final Logger LOG = LoggerFactory.getLogger(Player.class);
  private Long playerId;
  private String username;

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (!(o instanceof Player)) {
      return false;
    }
    Player other = (Player) o;
    return Objects.equals(playerId, other.getPlayerId());
  }

  public Long getPlayerId() {
    return playerId;
  }

  public void setPlayerId(Long playerId) {
    this.playerId = playerId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 47 * hash + Objects.hashCode(this.playerId);
    return hash;
  }

  @Override
  public String toString() {
    return "Player[playerId:" + playerId + ", username:" + username + "]";
  }
}
