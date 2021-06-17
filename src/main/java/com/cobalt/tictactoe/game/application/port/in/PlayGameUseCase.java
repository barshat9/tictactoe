package com.cobalt.tictactoe.game.application.port.in;

import com.cobalt.tictactoe.game.domain.GameStatus;

public interface PlayGameUseCase {

  GameStatus play(PlayGameCommand command);
}
