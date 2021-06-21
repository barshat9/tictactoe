package com.cobalt.tictactoe;

import com.cobalt.tictactoe.user.domain.User;
import com.cobalt.tictactoe.user.domain.UserRepository;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableAspectJAutoProxy
@Slf4j
public class Tictactoe2Application {

  public static void main(String[] args) {
    SpringApplication.run(Tictactoe2Application.class, args);
  }//  @Bean
//  DataSource dataSource() {
//    DataSourceBuilder builder = DataSourceBuilder.create();
//    builder.driverClassName("")
//  }




  @Bean
  @Profile("!prod")
  CommandLineRunner run(final UserRepository userRepository) {
    return args -> {
      var user1 =
          User.builder()
              .email("jd@test.com")
              .firstName("John")
              .lastName("Doe")
              .password("password")
              .build();
      userRepository.save(user1);

      var user2 = User.builder().email("mj@test.com").firstName("Mary").lastName("Jane").password("password").build();
      userRepository.save(user2);
      log.info("First Player Details => {} : {}", user1.getEmail(), user1.getPassword());
      log.info("Second Player Details => {} : {}", user2.getEmail(), user2.getPassword());
    };
  }
}
