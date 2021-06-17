package com.cobalt.tictactoe.game.application.port.in.eventhandlers;

import java.util.Map;

public interface GameEventHandler {

  Map<String, Object> handle(GameEventCommand command);
}
