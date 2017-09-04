package org.kalah.microservice.game.web.websocket.model;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Brighton Kukasira <brighton.kukasira@gmail.com>
 */
public class AddPlayerMessage {

  private static final Logger LOG = LoggerFactory.getLogger(AddPlayerMessage.class);
  private String requestId;
  private MessageType type;
  private String username;

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (!(o instanceof AddPlayerMessage)) {
      return false;
    }
    AddPlayerMessage other = (AddPlayerMessage) o;
    return Objects.equals(requestId, other.getRequestId());
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
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
    hash = 31 * hash + Objects.hashCode(this.requestId);
    return hash;
  }

  @Override
  public String toString() {
    return "AddPlayerMessage[requestId:" + requestId + ", type:" + type
            + ", username:" + username + "]";
  }
}
