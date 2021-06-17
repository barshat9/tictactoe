package com.cobalt.tictactoe.user.controller;

import lombok.Data;

@Data
public class CreateUserDTO {

  private String email;

  private String firstName;

  private String lastName;

  private String password;
}
