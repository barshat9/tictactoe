package com.cobalt.tictactoe.game.application.service.eventhandlers;

import com.cobalt.tictactoe.game.application.port.in.GameCRUDUseCase;
import com.cobalt.tictactoe.game.application.port.in.eventhandlers.GameEventCommand;
import com.cobalt.tictactoe.game.application.port.in.eventhandlers.GameEventHandler;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserQuitEventHandler implements GameEventHandler {

  private final GameCRUDUseCase gameCRUDUseCase;

  @Override
  public Map<String, Object> handle(GameEventCommand command) {
    var optGame = gameCRUDUseCase.findByPlayer(command.getPlayerID());
    if (optGame.isPresent()) {
      var game = optGame.get();
      game.quit();
      Long winnerPlayer =
          command.getPlayerID().equals(game.getFirstPlayer())
              ? game.getSecondPlayer()
              : game.getFirstPlayer();
      game.setWinnerPlayerID(winnerPlayer);
      game.setLoserPlayerID(command.getPlayerID());
      gameCRUDUseCase.update(game);
      return Map.of("eventType", 5, "recipient", winnerPlayer, "gameID", game.getId());
    }
    return Collections.emptyMap();
  }
}
