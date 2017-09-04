package org.kalah.microservice.game.web.websocket.model;

import java.util.LinkedHashMap;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Brighton Kukasira <brighton.kukasira@gmail.com>
 */
public class Board {

  private static final Object BOARD_LOCK = new Object();
  private static final Logger LOG = LoggerFactory.getLogger(Board.class);
  private Boolean anotherTurn = false;
  private Long boardId;
  private Boolean gameOver;
  private final LinkedHashMap<String, Integer> pits;
  private Player player1;
  private Player player2;
  private Player winnerPlayer;

  public Board() {
    this.pits = new LinkedHashMap<>(14);
    this.pits.put("pit-00", 0);
    this.pits.put("pit-01", 4);
    this.pits.put("pit-02", 4);
    this.pits.put("pit-03", 4);
    this.pits.put("pit-04", 4);
    this.pits.put("pit-05", 4);
    this.pits.put("pit-06", 4);
    this.pits.put("pit-07", 0);
    this.pits.put("pit-08", 4);
    this.pits.put("pit-09", 4);
    this.pits.put("pit-10", 4);
    this.pits.put("pit-11", 4);
    this.pits.put("pit-12", 4);
    this.pits.put("pit-13", 4);
  }

  public Boolean getAnotherTurn() {
    return anotherTurn;
  }

  public void setAnotherTurn(Boolean anotherTurn) {
    this.anotherTurn = anotherTurn;
  }

  public Long getBoardId() {
    return boardId;
  }

  public void setBoardId(Long boardId) {
    this.boardId = boardId;
  }

  public Boolean getGameOver() {
    return gameOver;
  }

  public void setGameOver(Boolean gameOver) {
    this.gameOver = gameOver;
  }

  public LinkedHashMap<String, Integer> getPits() {
    return pits;
  }

  public Player getPlayer1() {
    synchronized (BOARD_LOCK) {
      return player1;
    }
  }

  public void setPlayer1(Player player1) {
    synchronized (BOARD_LOCK) {
      this.player1 = player1;
    }
  }

  public Player getPlayer2() {
    synchronized (BOARD_LOCK) {
      return player2;
    }
  }

  public void setPlayer2(Player player2) {
    synchronized (BOARD_LOCK) {
      this.player2 = player2;
    }
  }

  public Player getWinnerPlayer() {
    return winnerPlayer;
  }

  public void setWinnerPlayer(Player winnerPlayer) {
    this.winnerPlayer = winnerPlayer;
  }

  public boolean isFull() {
    synchronized (BOARD_LOCK) {
      return player1 != null && player2 != null;
    }
  }

  public Player removePlayer(Long playerId) {
    Player removedPlayer = null;
    synchronized (BOARD_LOCK) {
      if (player1 != null
              && Objects.equals(player1.getPlayerId(), playerId)) {
        removedPlayer = player1;
        player1 = null;
        this.pits.put("pit-00", 0);
        this.pits.put("pit-07", 0);
      }
      if (removedPlayer == null
              && player2 != null
              && Objects.equals(player2.getPlayerId(), playerId)) {
        removedPlayer = player2;
        player2 = null;
        this.pits.put("pit-00", 0);
        this.pits.put("pit-07", 0);
      }
    }
    return removedPlayer;
  }

  public Player setIfNullPlayer1(Player player) {
    synchronized (BOARD_LOCK) {
      if (player1 != null) {
        return player1;
      }
      player1 = player;
      return null;
    }
  }

  public Player setIfNullPlayer2(Player player) {
    synchronized (BOARD_LOCK) {
      if (player2 != null) {
        return player2;
      }
      player2 = player;
      return null;
    }
  }
}
