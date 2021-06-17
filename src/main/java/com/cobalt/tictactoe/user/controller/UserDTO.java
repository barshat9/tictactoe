package com.cobalt.tictactoe.user.controller;

import lombok.Value;

@Value
public class UserDTO {

  Long id;

  String email;

  String firstName;

  String lastName;
}
