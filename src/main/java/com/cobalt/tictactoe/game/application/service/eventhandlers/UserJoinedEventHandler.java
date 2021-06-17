package com.cobalt.tictactoe.game.application.service.eventhandlers;

import com.cobalt.tictactoe.game.application.port.in.JoinGameCommand;
import com.cobalt.tictactoe.game.application.port.in.JoinGameUseCase;
import com.cobalt.tictactoe.game.application.port.in.eventhandlers.GameEventCommand;
import com.cobalt.tictactoe.game.application.port.in.eventhandlers.GameEventHandler;
import com.cobalt.tictactoe.user.domain.UserRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJoinedEventHandler implements GameEventHandler {

  private final JoinGameUseCase joinGameUseCase;

  private final UserRepository userRepository;

  @Override
  public Map<String, Object> handle(GameEventCommand command) {
    var game = joinGameUseCase.joinGame(new JoinGameCommand(command.getPlayerID()));
    var user =
        userRepository
            .findById(command.getPlayerID())
            .orElseThrow(() -> new IllegalStateException("No User" + command.getPlayerID()));
    String joinedUsername = user.getFirstName() + " " + user.getLastName();
    return Map.of(
        "eventType",
        1,
        "gameID",
        game.getId(),
        "initiatingPlayerID",
        command.getPlayerID(),
        "joinedPlayerID",
        command.getPlayerID(),
        "joinedPlayerName",
        joinedUsername);
  }
}
