package com.cobalt.tictactoe.game.application.service;

import com.cobalt.tictactoe.common.annotations.UseCase;
import com.cobalt.tictactoe.game.application.port.in.GameCRUDUseCase;
import com.cobalt.tictactoe.game.application.port.in.PlayGameCommand;
import com.cobalt.tictactoe.game.application.port.in.PlayGameUseCase;
import com.cobalt.tictactoe.game.domain.Game;
import com.cobalt.tictactoe.game.domain.GameStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class PlayGameService implements PlayGameUseCase {

  private final GameCRUDUseCase gameCRUDUseCase;

  @Override
  public GameStatus play(PlayGameCommand command) {
    log.info("Making Move {} For Player {}", command.getPosition(), command.getPlayerID());
    var game = gameCRUDUseCase.retrieve(command.getGameID());
    validateGameState(game);
    if (game.getStatus() == GameStatus.STARTED) {
      game.inProgress();
    }
    var status = game.makeMove(command.getPlayerID(), command.getPosition());
    gameCRUDUseCase.update(game);
    return status;
  }

  void validateGameState(Game game) {
    var status = game.getStatus();
    if (status == GameStatus.DRAW || status == GameStatus.WON_LOST || status == GameStatus.IN_LOBBY ) {
      throw new IllegalStateException("Not A Valid Status To Make A Move " + status);
    }
  }
}
