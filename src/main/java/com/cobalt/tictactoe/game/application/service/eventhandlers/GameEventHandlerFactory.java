package com.cobalt.tictactoe.game.application.service.eventhandlers;

import com.cobalt.tictactoe.game.application.port.in.JoinGameUseCase;
import com.cobalt.tictactoe.game.application.port.in.PlayGameUseCase;
import com.cobalt.tictactoe.game.application.port.in.eventhandlers.GameEvent;
import com.cobalt.tictactoe.game.application.port.in.eventhandlers.GameEventHandler;
import com.cobalt.tictactoe.user.domain.UserRepository;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GameEventHandlerFactory {

  private Map<Class<? extends GameEventHandler>, GameEventHandler> handlers = new HashMap<>();

  private final JoinGameUseCase joinGameUseCase;

  private final PlayGameUseCase playGameUseCase;

  private final UserRepository userRepository;

  @PostConstruct
  public void init() {
    handlers.put(UserJoinedEventHandler.class, userJoinedEventHandler());
    handlers.put(UserPlayedEventHandler.class, userPlayedEventHandler());
  }

  public GameEventHandler gameEventHandler(int eventType) {
    switch (eventType) {
      case GameEvent.USER_JOINED:
        return handlers.get(UserJoinedEventHandler.class);
      case GameEvent.USER_PLAYED:
        return handlers.get(UserPlayedEventHandler.class);
      default:
        return null;
    }
  }

  @Bean
  UserJoinedEventHandler userJoinedEventHandler() {
    return new UserJoinedEventHandler(joinGameUseCase, userRepository);
  }

  @Bean
  UserPlayedEventHandler userPlayedEventHandler() {
    return new UserPlayedEventHandler(playGameUseCase);
  }
}
