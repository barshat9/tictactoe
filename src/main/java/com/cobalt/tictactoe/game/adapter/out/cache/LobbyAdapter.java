package com.cobalt.tictactoe.game.adapter.out.cache;

import com.cobalt.tictactoe.game.application.port.out.FindInLobbyPort;
import com.cobalt.tictactoe.game.application.port.out.PutInLobbyPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LobbyAdapter implements FindInLobbyPort, PutInLobbyPort {

  private final InMemorySyncQueue inMemorySyncQueue;

  @Override
  public Optional<Long> findAnyWaitingGame() {
    return Optional.ofNullable(inMemorySyncQueue.removeFromLobby());
  }

  @Override
  public void put(Long gameID) {
    inMemorySyncQueue.addToLobby(gameID);
  }
}
