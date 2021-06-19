package com.cobalt.tictactoe.game.application.port.in;

import java.util.List;

public interface GameHistoryUseCase {

  List<GameHistory> findGameHistory(Long forPlayerID);
}
