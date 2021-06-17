package com.cobalt.tictactoe.game.application.port.out;

import java.util.Optional;

public interface FindInLobbyPort {

  Optional<Long> findAnyWaitingGame();
}
