package com.cobalt.tictactoe.game.application.port.in;

import lombok.Value;

@Value
public class PlayGameCommand {

  private Long gameID;

  private Long playerID;

  private int position;
}
