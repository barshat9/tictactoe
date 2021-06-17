package com.cobalt.tictactoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Tictactoe2Application {

	public static void main(String[] args) {
		SpringApplication.run(Tictactoe2Application.class, args);
	}

}
