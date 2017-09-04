package org.kalah.microservice.game.web.websocket;

/**
 *
 * @author Brighton Kukasira <brighton.kukasira@gmail.com>
 */
import org.kalah.microservice.game.web.exceptions.BoardNotFoundException;
import org.kalah.microservice.game.web.websocket.model.AddPlayerMessage;
import org.kalah.microservice.game.web.websocket.model.Board;
import org.kalah.microservice.game.web.websocket.model.Boards;
import org.kalah.microservice.game.web.websocket.model.MessageType;
import org.kalah.microservice.game.web.websocket.model.Player;
import org.kalah.microservice.game.web.websocket.model.PlayerMoveMessage;
import org.kalah.microservice.game.web.websocket.model.PlayerResponse;
import org.kalah.microservice.game.web.websocket.model.Players;
import static org.kalah.microservice.game.web.websocket.model.Players.createPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

  private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

  public static PlayerResponse createResponse(final Board board,
          MessageType type, String username, String requestId) {
    final PlayerResponse response = new PlayerResponse();
    response.setBoard(board);
    response.setRequestId(requestId);
    response.setType(type);
    updatePreviousPlayerUsername(board, username, response);
    return response;
  }

  public static Long getOrCreatePlayerId(SimpMessageHeaderAccessor headerAccessor) {
    Long playerId = (Long) headerAccessor.getSessionAttributes().get("playerId");
    if (playerId == null) {
      playerId = Players.ID_GENERATOR.incrementAndGet();
      headerAccessor.getSessionAttributes().put("playerId", playerId);
    }
    return playerId;
  }

  private static void updatePreviousPlayerUsername(final Board board,
          String username, final PlayerResponse response) {
    if (board.getAnotherTurn()) {
      if (board.getPlayer1().getUsername().equals(username)) {
        response.setUsername(board.getPlayer2().getUsername());
      } else {
        response.setUsername(board.getPlayer1().getUsername());
      }
    } else {
      response.setUsername(username);
    }
  }

  @MessageMapping("/game.addPlayer")
  @SendTo("/topic/public")
  public PlayerResponse addPlayer(@Payload AddPlayerMessage message,
          SimpMessageHeaderAccessor headerAccessor) {
    try {
      Players.checkUsernameUniqueness(message.getUsername());
      // Add username in web socket session
      headerAccessor.getSessionAttributes().put("username", message.getUsername());
      Long playerId = getOrCreatePlayerId(headerAccessor);
      Boards.removePlayer(playerId);
      Board board = getOrCreateBoard(createPlayer(playerId, message.getUsername()), headerAccessor);
      return createResponse(board, message.getType(), message.getUsername(), message.getRequestId());
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }

  @MessageExceptionHandler
  @SendToUser("/queue/errors")
  public String handleException(Throwable exception) {
    return exception.getMessage();
  }

  @MessageMapping("/game.playerMove")
  @SendTo("/topic/public")
  public PlayerResponse playerMove(@Payload PlayerMoveMessage message,
          SimpMessageHeaderAccessor headerAccessor) {
    Board board = null;
    String username = null;
    try {
      board = Boards.get(message.getBoardId());
      if (board == null) {
        throw new BoardNotFoundException();
      }
      username = (String) headerAccessor.getSessionAttributes().get("username");
      Boards.applyMove(board, message.getPlayerId(), message.getPitId());
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
    return createResponse(board, message.getType(), username, message.getRequestId());
  }

  private Board getOrCreateBoard(final Player player, SimpMessageHeaderAccessor headerAccessor) {
    final Board board = Boards.assignAnyBoard(player);
    headerAccessor.getSessionAttributes().put("boardId", board.getBoardId());
    return board;
  }
}
