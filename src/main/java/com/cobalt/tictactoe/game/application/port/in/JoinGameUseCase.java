package com.cobalt.tictactoe.game.application.port.in;

import com.cobalt.tictactoe.game.domain.Game;

public interface JoinGameUseCase {

  /**
   *
   * @param command Contains Requesting Player's ID
   * @return The Game Object
   *
   * Game will be searched in the Lobby, If Not Found a new Game will be created with IN_LOBBY status
   */
  Game joinGame(final JoinGameCommand command);
}
