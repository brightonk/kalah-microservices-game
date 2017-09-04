package org.kalah.microservice.game.web.websocket;

/**
 *
 * @author Brighton Kukasira <brighton.kukasira@gmail.com>
 */
import static org.kalah.microservice.game.web.websocket.GameController.createResponse;
import org.kalah.microservice.game.web.websocket.model.Board;
import org.kalah.microservice.game.web.websocket.model.Boards;
import org.kalah.microservice.game.web.websocket.model.MessageType;
import org.kalah.microservice.game.web.websocket.model.PlayerResponse;
import org.kalah.microservice.game.web.websocket.model.Players;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);

  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    LOGGER.info("Received a new web socket connection");
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

    String username = (String) headerAccessor.getSessionAttributes().get("username");
    if (username == null) {
      return;
    }
    LOGGER.info("User Disconnected : " + username);
    Players.removeUsername(username);
    Board board = null;
    Long boardId = (Long) headerAccessor.getSessionAttributes().get("boardId");
    if (boardId != null) {
      board = Boards.get(boardId);
    }
    Long playerId = (Long) headerAccessor.getSessionAttributes().get("playerId");
    if (playerId != null) {
      Boards.removePlayer(playerId);
    }
    PlayerResponse response = createResponse(board, MessageType.LEAVE, username, null);
    messagingTemplate.convertAndSend("/topic/public", response);
  }
}
