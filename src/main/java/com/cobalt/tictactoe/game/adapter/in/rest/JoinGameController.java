package com.cobalt.tictactoe.game.adapter.in.rest;

import com.cobalt.tictactoe.game.application.port.in.JoinGameCommand;
import com.cobalt.tictactoe.game.application.port.in.JoinGameUseCase;
import com.cobalt.tictactoe.game.domain.Game;
import com.cobalt.tictactoe.game.domain.GameStatus;
import com.cobalt.tictactoe.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("game")
@RequiredArgsConstructor
public class JoinGameController {

  private final JoinGameUseCase joinGameUseCase;

  private final UserRepository userRepository;

  @PostMapping("join")
  public ResponseEntity<JoinGameResponse> join(@RequestBody JoinGameRequest req) {
    var game = joinGameUseCase.joinGame(new JoinGameCommand(req.getUserID()));
    String opponent = "";
    if (game.getStatus() != GameStatus.IN_LOBBY) {
      opponent = getOpponentName(game);
    }
    return ResponseEntity.ok(new JoinGameResponse(game.getId(), game.getStatus().name(), opponent));
  }

  private String getOpponentName(Game game) {
    var user = userRepository.findById(game.getFirstPlayer()).orElseThrow(() -> new IllegalStateException("No User " + game.getFirstPlayer()));
    return user.getFirstName() + " " + user.getLastName();
  }
}
