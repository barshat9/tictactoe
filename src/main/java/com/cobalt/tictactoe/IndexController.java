package com.cobalt.tictactoe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @GetMapping
  public String index() {
    return "index";
  }

  @GetMapping("/playground")
  public String playground() {
    return "playground";
  }

  @GetMapping("/dashboard")
  public String dashboard() {
    return "dashboard";
  }
}
