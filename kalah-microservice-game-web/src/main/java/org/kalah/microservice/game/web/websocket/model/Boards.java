package org.kalah.microservice.game.web.websocket.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Brighton Kukasira <brighton.kukasira@gmail.com>
 */
public interface Boards {

  // TODO: add clean up so that unused boards are removed from collection
  public static final ConcurrentHashMap<Long, Board> BOARDS
          = new ConcurrentHashMap<>(0);
  public static final AtomicLong ID_GENERATOR = new AtomicLong(0);
  public static final Logger LOG = LoggerFactory.getLogger(Boards.class);
  public static final String PLAYER1_STORE = "pit-07";
  public static final String PLAYER2_STORE = "pit-00";

  public static void applyMove(Board board, Long playerId, String pitId) {
    int seeds = board.getPits().get(pitId);
    if (seeds == 0) {
      return;
    }
    board.getPits().put(pitId, 0);

    boolean iamPlayerOne = board.getPlayer1().getPlayerId().equals(playerId);
    String myStore = iamPlayerOne ? PLAYER1_STORE : PLAYER2_STORE;
    String opponetStore = iamPlayerOne ? PLAYER2_STORE : PLAYER1_STORE;
    String[] myPits = iamPlayerOne ? PLAYER1_PITS : PLAYER2_PITS_REVERSED;
    String[] opponentPits = iamPlayerOne ? PLAYER2_PITS : PLAYER1_PITS_REVERSED;

    final LinkedList<String> keys = new LinkedList<>(board.getPits().keySet());
    String key = dropSeeds(keys, pitId, seeds, opponetStore, board);
    board.setAnotherTurn(false);
    if (key.equals(myStore)) {
      board.setAnotherTurn(true);
    }
    captureSeedsOnLastDrop(board, key, myPits, opponentPits, myStore);
    checkGameOver(myPits, board, opponentPits, myStore, opponetStore, iamPlayerOne);
  }

  public static String dropSeeds(final LinkedList<String> keys, String pitId,
          int seeds, String opponetStore, Board board) {
    String key = null;
    int index = keys.indexOf(pitId);
    while (seeds > 0) {
      index++;
      if (index >= keys.size()) {
        index = 0;
      }
      key = keys.get(index);
      if (key.equals(opponetStore)) {
        continue;
      }
      Integer currentSeeds = board.getPits().get(key);
      board.getPits().put(key, currentSeeds + 1);
      seeds--;
    }
    return key;
  }

  public static void checkGameOver(String[] myPits, Board board,
          String[] opponentPits, String myStore, String opponetStore,
          boolean iamPlayerOne) {
    boolean gameOverMySide = true;
    for (int i = 0; i < myPits.length; i++) {
      String myPit = myPits[i];
      if (board.getPits().get(myPit) > 0) {
        gameOverMySide = false;
        break;
      }
    }
    boolean gameOverOpponentSide = true;
    for (int i = 0; i < opponentPits.length; i++) {
      String opponentPit = opponentPits[i];
      if (board.getPits().get(opponentPit) > 0) {
        gameOverOpponentSide = false;
        break;
      }
    }
    if (gameOverMySide || gameOverOpponentSide) {
      updateWinner(board, myStore, opponetStore, iamPlayerOne);
    }
  }

  public static Board assignAnyBoard(Player player) {
    Board selectedBoard = null;
    for (Iterator<Map.Entry<Long, Board>> iterator
            = BOARDS.entrySet().iterator(); iterator.hasNext();) {
      final Board board = iterator.next().getValue();
      if (board.isFull()) {
        continue;
      }
      final Player existingPlayer1 = board.setIfNullPlayer1(player);
      if (existingPlayer1 == null) {
        selectedBoard = board;
        break;
      }
      final Player existingPlayer2 = board.setIfNullPlayer2(player);
      if (existingPlayer2 == null) {
        selectedBoard = board;
        break;
      }
    }
    if (selectedBoard == null) {
      selectedBoard = new Board();
      selectedBoard.setBoardId(ID_GENERATOR.incrementAndGet());
      selectedBoard.setPlayer1(player);
      BOARDS.put(selectedBoard.getBoardId(), selectedBoard);
    }
    return selectedBoard;
  }

  public static void captureSeedsOnLastDrop(Board board, String key,
          String[] myPits, String[] opponentPits, String myStore) {
    Integer currentSeeds = board.getPits().get(key);
    if (currentSeeds == 1) {
      int lastPitDrop = java.util.Arrays.binarySearch(myPits, key);
      if (lastPitDrop < 0) {
        return;
      }
      String opponetKey = opponentPits[lastPitDrop];
      Integer opponentSeeds = board.getPits().get(opponetKey);
      Integer myStoreSeeds = board.getPits().get(myStore);
      board.getPits().put(opponetKey, 0);
      board.getPits().put(key, 0);
      board.getPits().put(myStore, myStoreSeeds + opponentSeeds + currentSeeds);
    }
  }

  public static Board get(Long boardId) {
    return BOARDS.get(boardId);
  }

  public static void removePlayer(Long playerId) {
    for (Iterator<Map.Entry<Long, Board>> iterator
            = BOARDS.entrySet().iterator(); iterator.hasNext();) {
      iterator.next().getValue().removePlayer(playerId);
    }
  }

  public static Board removePlayerFromBoard(Long boardId, Long playerId) {
    if (boardId == null) {
      return null;
    }
    if (playerId == null) {
      return null;
    }
    final Board board = BOARDS.get(boardId);
    board.removePlayer(playerId);
    return board;
  }

  public static void updateWinner(Board board, String myStore, String opponetStore, boolean iamPlayerOne) {
    board.setGameOver(true);
    int myScore = board.getPits().get(myStore);
    int opponentScore = board.getPits().get(opponetStore);
    if (myScore > opponentScore) {
      board.setWinnerPlayer(iamPlayerOne ? board.getPlayer1() : board.getPlayer2());
    } else if (myScore < opponentScore) {
      board.setWinnerPlayer(iamPlayerOne ? board.getPlayer2() : board.getPlayer1());
    }
  }
  public String[] PLAYER1_PITS = {
    "pit-01",
    "pit-02",
    "pit-03",
    "pit-04",
    "pit-05",
    "pit-06"
  };
  public String[] PLAYER1_PITS_REVERSED = {
    "pit-06",
    "pit-05",
    "pit-04",
    "pit-03",
    "pit-02",
    "pit-01"
  };
  public String[] PLAYER2_PITS = {
    "pit-13",
    "pit-12",
    "pit-11",
    "pit-10",
    "pit-09",
    "pit-08"
  };
  public String[] PLAYER2_PITS_REVERSED = {
    "pit-08",
    "pit-09",
    "pit-10",
    "pit-11",
    "pit-12",
    "pit-13"
  };
}
