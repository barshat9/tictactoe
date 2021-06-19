package com.cobalt.tictactoe.game.application.port.in;

import com.cobalt.tictactoe.game.domain.Game;
import java.util.List;
import java.util.Optional;

public interface GameCRUDUseCase {

  Game create(Game game);

  Game retrieve(Long id);

  void update(Game game);

  Optional<Game> findByPlayer(Long playerID);

  List<Game> findAllGamesByPlayer(Long playerID);
}
