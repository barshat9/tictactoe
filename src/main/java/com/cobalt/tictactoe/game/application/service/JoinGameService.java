package com.cobalt.tictactoe.game.application.service;

import com.cobalt.tictactoe.common.annotations.UseCase;
import com.cobalt.tictactoe.game.application.port.in.GameCRUDUseCase;
import com.cobalt.tictactoe.game.application.port.in.JoinGameCommand;
import com.cobalt.tictactoe.game.application.port.in.JoinGameUseCase;
import com.cobalt.tictactoe.game.application.port.out.FindInLobbyPort;
import com.cobalt.tictactoe.game.application.port.out.PutInLobbyPort;
import com.cobalt.tictactoe.game.domain.Game;
import com.cobalt.tictactoe.game.domain.GameBuilder;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class JoinGameService implements JoinGameUseCase {

  private final FindInLobbyPort findInLobbyPort;

  private final GameCRUDUseCase gameCRUDUseCase;

  private final PutInLobbyPort putInLobbyPort;

  @Override
  public Game joinGame(JoinGameCommand command) {
    log.info("Joining Game {}", command.getPlayerID());
    return findAlreadyExisting(command.getPlayerID())
        .orElseGet(() -> findInLobby(command).orElseGet(() -> createNewGame(command)));
  }

  private Optional<Game> findAlreadyExisting(Long playerID) {
    log.info("Finding Game For Player {} If Already Exists", playerID);
    return gameCRUDUseCase.findByPlayer(playerID);
  }

  private Optional<Game> findInLobby(JoinGameCommand command) {
    log.info("Finding In ");
    return findInLobbyPort
        .findAnyWaitingGame()
        .map(gameCRUDUseCase::retrieve)
        .map(
            game -> {
              game.start();
              game.setSecondPlayer(command.getPlayerID());
              gameCRUDUseCase.update(game);
              return game;
            });
  }
  private Game createNewGame(JoinGameCommand command) {
    log.info("Creating New Game In Lobby");
    Game game = new GameBuilder.NewGameBuilder().forPlayer(command.getPlayerID()).build();
    var persistedGame = gameCRUDUseCase.create(game);
    putInLobbyPort.put(persistedGame.getId());
    return persistedGame;
  }
}
