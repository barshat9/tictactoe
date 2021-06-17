package com.cobalt.tictactoe.game.adapter.in.socket;

import com.cobalt.tictactoe.game.application.port.in.GameCRUDUseCase;
import com.cobalt.tictactoe.game.application.port.in.eventhandlers.GameEventCommand;
import com.cobalt.tictactoe.game.application.service.eventhandlers.GameEventHandlerFactory;
import com.cobalt.tictactoe.user.domain.UserRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GameSocketController {

  private final SimpMessagingTemplate simpMessagingTemplate;

  private final GameEventHandlerFactory factory;

  private final GameCRUDUseCase gameCRUDUseCase;

  @MessageMapping("/game-controller")
  public void process(@Payload GameEventCommand command) {
    log.info("Sending Message {}", command);
    var handler = factory.gameEventHandler(command.getEventType());
    var resp = handler.handle(command);
    var result = addReceiverID(resp, command.getGameID(), command.getPlayerID());
    simpMessagingTemplate.convertAndSendToUser(
        String.valueOf(command.getGameID()), "/queue/game-events", result);
  }

  private Map<String, Object>  addReceiverID(Map<String, Object> resp, Long gameID, Long playerID) {
    var game = gameCRUDUseCase.retrieve(gameID);
    Long destinationPlayerID =
        playerID.equals(game.getFirstPlayer()) ? game.getSecondPlayer() : game.getFirstPlayer();
    Map<String, Object> result = new HashMap<>(resp);
    result.put("recipient", destinationPlayerID);
    return result;
  }
}
