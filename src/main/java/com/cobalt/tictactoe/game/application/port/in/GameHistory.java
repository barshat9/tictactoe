package com.cobalt.tictactoe.game.application.port.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class GameHistory {

  private Long id;

  @JsonIgnore
  private Long forPlayerID;

  private String firstPlayerName;

  private String secondPlayerName;

  private Date startedDate;

  private int gameTimeInSeconds;

  private String status;
}
