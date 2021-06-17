package com.cobalt.tictactoe.game.adapter.in.rest;

import lombok.Data;
import lombok.Value;

@Value
public class JoinGameResponse {

  Long gameID;

  String status;

  String opponent;
}
