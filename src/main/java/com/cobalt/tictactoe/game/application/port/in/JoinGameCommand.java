package com.cobalt.tictactoe.game.application.port.in;

import com.cobalt.tictactoe.common.annotations.UseCaseCommand;
import lombok.Value;

@UseCaseCommand
@Value
public class JoinGameCommand {

  Long playerID;
}
