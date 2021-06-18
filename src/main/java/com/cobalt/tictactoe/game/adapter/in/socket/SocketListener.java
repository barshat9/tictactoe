package com.cobalt.tictactoe.game.adapter.in.socket;

import com.cobalt.tictactoe.game.application.port.in.GameCRUDUseCase;
import com.cobalt.tictactoe.game.application.port.in.eventhandlers.GameEventCommand;
import com.cobalt.tictactoe.game.application.service.eventhandlers.GameEventHandlerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class SocketListener {

  private final GameEventHandlerFactory factory;

  private final SimpMessagingTemplate simpMessagingTemplate;

  @EventListener
  public void handleSessionConnected(SessionConnectEvent event) {
    var headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
    var user = headers.getUser();
    if (user == null) {
      log.error("No Principal Found");
      return;
    }
    log.info("User Found {} ", user.getName());
  }

  @EventListener
  public void handleSessionDisconnected(SessionDisconnectEvent event) {
    var headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
    var user = headers.getUser();
    if (user == null) {
      log.error("No Principal Found");
      return;
    }
    var handler = factory.gameEventHandler(5);
    var message = handler.handle(new GameEventCommand(5, null, Long.valueOf(user.getName()), -1));
    var gameID = Long.parseLong(String.valueOf(message.get("gameID")));
    log.info("User Disconnecting {} ", user.getName());
    simpMessagingTemplate.convertAndSendToUser(
        String.valueOf(gameID), "/queue/game-events", message);
  }
}
