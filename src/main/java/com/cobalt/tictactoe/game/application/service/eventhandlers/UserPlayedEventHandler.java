package com.cobalt.tictactoe.game.application.service.eventhandlers;

import com.cobalt.tictactoe.game.application.port.in.PlayGameCommand;
import com.cobalt.tictactoe.game.application.port.in.PlayGameUseCase;
import com.cobalt.tictactoe.game.application.port.in.eventhandlers.GameEventCommand;
import com.cobalt.tictactoe.game.application.port.in.eventhandlers.GameEventHandler;
import com.cobalt.tictactoe.game.domain.GameStatus;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPlayedEventHandler implements GameEventHandler {

  private final PlayGameUseCase playGameUseCase;

  @Override
  public Map<String, Object> handle(GameEventCommand command) {
    var gameStatus =
        playGameUseCase.play(
            new PlayGameCommand(command.getGameID(), command.getPlayerID(), command.getPosition()));
    if (gameStatus == GameStatus.IN_PROGRESS) {
      return Map.of(
          "eventType",
          2,
          "gameID",
          command.getGameID(),
          "status",
          gameStatus.name(),
          "position",
          command.getPosition());
    }
    if (gameStatus == GameStatus.WON_LOST) {
      return Map.of(
          "eventType", 3, "gameID", command.getGameID(), "winnerPlayerID", command.getPlayerID());
    }

    if (gameStatus == GameStatus.DRAW) {
      return Map.of("eventType", 4);
    }
    return Map.of("", ""); // TODO If won or draw
  }
}
