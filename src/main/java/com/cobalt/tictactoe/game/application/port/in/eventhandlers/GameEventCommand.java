package com.cobalt.tictactoe.game.application.port.in.eventhandlers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GameEventCommand {

  private int eventType;

  private Long gameID;

  private Long playerID;

  private Integer position;

}
