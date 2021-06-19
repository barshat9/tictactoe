package com.cobalt.tictactoe.user.controller;

import com.cobalt.tictactoe.game.application.port.in.GameHistory;
import com.cobalt.tictactoe.game.application.port.in.GameHistoryUseCase;
import com.cobalt.tictactoe.user.domain.User;
import com.cobalt.tictactoe.user.domain.UserRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;

  private final GameHistoryUseCase gameHistoryUseCase;

  @PostMapping
  public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
    var user =
        User.builder()
            .email(createUserDTO.getEmail())
            .firstName(createUserDTO.getFirstName())
            .lastName(createUserDTO.getLastName())
            .password(createUserDTO.getPassword())
            .build();
    var persisted = userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new UserDTO(
                persisted.getId(),
                persisted.getEmail(),
                persisted.getFirstName(),
                persisted.getLastName()));
  }

  @PostMapping("login")
  public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
    var user =
        userRepository
            .findByEmail(loginDTO.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Bad Credentials"));
    if (!loginDTO.getPassword().equals(user.getPassword())) {
      throw new IllegalStateException("Invalid Password");
    }
    return ResponseEntity.ok(Map.of("userID", user.getId()));
  }

  @GetMapping("/{id}/games")
  public ResponseEntity<List<GameHistory>> findPlayerGames(@PathVariable("id") Long id) {
    return ResponseEntity.ok(gameHistoryUseCase.findGameHistory(id));
  }
}
