package com.cobalt.tictactoe.game.application.port.out;

import com.cobalt.tictactoe.game.domain.Game;
import java.util.Optional;

public interface GameCRUDPort {

  Game findByID(Long gameID);

  Game save(Game game);

  Optional<Game> findByPlayerID(Long playerID);
}
