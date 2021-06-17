package com.cobalt.tictactoe.game.adapter.out.cache;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.stereotype.Component;

@Component
public class InMemorySyncQueue {

  Queue<Long> lobby = new ConcurrentLinkedQueue<>();

  public void addToLobby(Long gameID) {
    if (lobby.contains(gameID)) {
      throw new RuntimeException("Duplicate Entry Not Allowed");
    }
    lobby.add(gameID);
  }

  public synchronized Long removeFromLobby() {
    if (lobby.isEmpty()) {
      return null;
    }
    return lobby.remove();
  }

  public int getCurrentLobbySize() {
    return lobby.size();
  }
}
