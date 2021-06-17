package com.cobalt.tictactoe.user.controller;

import com.cobalt.tictactoe.user.domain.User;
import com.cobalt.tictactoe.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;

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
}
