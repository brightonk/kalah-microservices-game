package org.kalah.microservice.game.web.websocket.model;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Brighton Kukasira <brighton.kukasira@gmail.com>
 */
public class PlayerMoveMessage {

  private static final Logger LOG = LoggerFactory.getLogger(PlayerMoveMessage.class);
  private Long boardId;
  private String pitId;
  private Long playerId;
  private String requestId;
  private Integer seeds;
  private MessageType type;

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (!(o instanceof PlayerMoveMessage)) {
      return false;
    }
    PlayerMoveMessage other = (PlayerMoveMessage) o;
    return Objects.equals(requestId, other.getRequestId());
  }

  public Long getBoardId() {
    return boardId;
  }

  public void setBoardId(Long boardId) {
    this.boardId = boardId;
  }

  public String getPitId() {
    return pitId;
  }

  public void setPitId(String pitId) {
    this.pitId = pitId;
  }

  public Long getPlayerId() {
    return playerId;
  }

  public void setPlayerId(Long playerId) {
    this.playerId = playerId;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public Integer getSeeds() {
    return seeds;
  }

  public void setSeeds(Integer seeds) {
    this.seeds = seeds;
  }

  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + Objects.hashCode(this.requestId);
    return hash;
  }

  @Override
  public String toString() {
    return "PlayerMoveMessage["
            + "requestId:" + requestId
            + ", type:" + type
            + ", playerId:" + playerId
            + ", boardId:" + boardId
            + ", pitId:" + pitId
            + ", seeds:" + seeds
            + "]";
  }
}
