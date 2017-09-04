package org.kalah.microservice.game.web.websocket.model;

import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.kalah.microservice.game.web.websocket.model.Boards.PLAYER1_PITS;
import static org.kalah.microservice.game.web.websocket.model.Boards.PLAYER2_PITS;

/**
 *
 * @author Brighton Kukasira <brighton.kukasira@gmail.com>
 */
public class BoardsTest {

  public BoardsTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of applyMove method, of class Boards.
   */
  @Test
  public void testApplyMove() {
    System.out.println("applyMove");
    Long playerId = 1L;
    Player player1 = new Player();
    player1.setPlayerId(1L);
    player1.setUsername("Player 1");
    Player player2 = new Player();
    player2.setPlayerId(2L);
    player2.setUsername("Player 2");
    Boards.assignAnyBoard(player1);
    Board board = Boards.assignAnyBoard(player2);
    String pitId = "pit-03";
    Boards.applyMove(board, playerId, pitId);
    assertTrue(board.getPits().get(pitId) == 0);
    assertTrue(board.getPits().get("pit-04") == 5);
  }

  /**
   * Test of dropSeeds method, of class Boards.
   */
  @Test
  public void testDropSeeds() {
    System.out.println("dropSeeds");
    Board board = new Board();
    LinkedList<String> keys = new LinkedList<>(board.getPits().keySet());;
    String pitId = "pit-03";
    int seeds = 1;
    String opponetStore = "pit-00";
    String expResult = "pit-04";
    String result = Boards.dropSeeds(keys, pitId, seeds, opponetStore, board);
    assertEquals(expResult, result);
  }

  /**
   * Test of checkGameOver method, of class Boards.
   */
  @Test
  public void testCheckGameOver() {
    System.out.println("checkGameOver");
    String[] myPits = PLAYER1_PITS;
    Board board = new Board();
    String[] opponentPits = PLAYER2_PITS;
    String myStore = "pit-07";
    String opponetStore = "pit-00";
    boolean iamPlayerOne = true;
    Boards.checkGameOver(myPits, board, opponentPits, myStore, opponetStore, iamPlayerOne);
    Boolean expResult = false;
    assertNull(board.getGameOver());
  }

  /**
   * Test of assignAnyBoard method, of class Boards.
   */
  @Test
  public void testAssignAnyBoard() {
    System.out.println("assignAnyBoard");
    Player player = new Player();
    player.setPlayerId(1L);
    player.setUsername("Player 1");
    Long expResult = 1L;
    Board result = Boards.assignAnyBoard(player);
    assertNotNull(result.getBoardId());
  }

  /**
   * Test of captureSeedsOnLastDrop method, of class Boards.
   */
  @Test
  public void testCaptureSeedsOnLastDrop() {
    System.out.println("captureSeedsOnLastDrop");
    Board board = new Board();
    String key = "pit-01";
    board.getPits().put(key, 1);
    String[] myPits = PLAYER1_PITS;
    String[] opponentPits = PLAYER2_PITS;
    String myStore = "pit-07";
    int lastPitDrop = java.util.Arrays.binarySearch(myPits, key);
    String opponetKey = opponentPits[lastPitDrop];
    Boards.captureSeedsOnLastDrop(board, key, myPits, opponentPits, myStore);
    Integer opponentSeeds = board.getPits().get(opponetKey);
    Integer expResult = 0;
    assertEquals(expResult, opponentSeeds);
  }

  /**
   * Test of get method, of class Boards.
   */
  @Test
  public void testGet() {
    System.out.println("get");
    Long boardId = 1L;
    Player player = new Player();
    player.setPlayerId(1L);
    player.setUsername("Player 1");
    Board expResult = Boards.assignAnyBoard(player);
    Board result = Boards.get(boardId);
    assertNotNull(result);
  }

  /**
   * Test of removePlayer method, of class Boards.
   */
  @Test
  public void testRemovePlayer() {
    System.out.println("removePlayer");
    Long playerId = 1L;
    Player player = new Player();
    player.setPlayerId(playerId);
    player.setUsername("Player 1");
    Boards.assignAnyBoard(player);
    Boards.removePlayer(playerId);
  }

  /**
   * Test of removePlayerFromBoard method, of class Boards.
   */
  @Test
  public void testRemovePlayerFromBoard() {
    System.out.println("removePlayerFromBoard");
    Long boardId = 1L;
    Long playerId = 1L;
    Player player = new Player();
    player.setPlayerId(playerId);
    player.setUsername("Player 1");
    Board board = Boards.assignAnyBoard(player);
    Board expResult = new Board();
    Board result = Boards.removePlayerFromBoard(board.getBoardId(), playerId);
    assertEquals(board.getBoardId(), result.getBoardId());
  }

  /**
   * Test of updateWinner method, of class Boards.
   */
  @Test
  public void testUpdateWinner() {
    System.out.println("updateWinner");
    Board board = new Board();
    String myStore = "pit-07";
    String opponetStore = "pit-00";
    boolean iamPlayerOne = true;
    Boards.updateWinner(board, myStore, opponetStore, iamPlayerOne);
    Boolean expResult = true;
    assertEquals(expResult, board.getGameOver());
    assertNull(board.getWinnerPlayer());
  }

}
