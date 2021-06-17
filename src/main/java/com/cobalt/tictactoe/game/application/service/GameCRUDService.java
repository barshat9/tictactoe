package com.cobalt.tictactoe.game.application.service;

import com.cobalt.tictactoe.common.annotations.UseCase;
import com.cobalt.tictactoe.game.application.port.in.GameCRUDUseCase;
import com.cobalt.tictactoe.game.application.port.out.GameCRUDPort;
import com.cobalt.tictactoe.game.domain.Game;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GameCRUDService implements GameCRUDUseCase {

  private final GameCRUDPort gameCRUDPort;

  @Override
  public Game create(Game game) {
    return gameCRUDPort.save(game);
  }

  @Override
  public Game retrieve(Long id) {
    return gameCRUDPort.findByID(id);
  }

  @Override
  public void update(Game game) {
    create(game);
  }

  @Override
  public Optional<Game> findByPlayer(Long playerID) {
    return gameCRUDPort.findByPlayerID(playerID);
  }
}
