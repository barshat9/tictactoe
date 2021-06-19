package com.cobalt.tictactoe.game.application.service;

import static org.junit.jupiter.api.Assertions.*;

import com.cobalt.tictactoe.game.application.port.in.GameCRUDUseCase;
import com.cobalt.tictactoe.game.application.port.in.GameHistory;
import com.cobalt.tictactoe.game.domain.Game;
import com.cobalt.tictactoe.game.domain.GameStatus;
import com.cobalt.tictactoe.user.domain.User;
import com.cobalt.tictactoe.user.domain.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameHistoryServiceTest {

  @Mock
  GameCRUDUseCase gameCRUDUseCase;

  @Mock
  UserRepository userRepository;

  GameHistoryService gameHistoryService;

  @BeforeEach
  public void before() {
    gameHistoryService = new GameHistoryService(gameCRUDUseCase, userRepository);
  }


  @Test
  void findGameHistory() {

    Mockito.when(gameCRUDUseCase.findAllGamesByPlayer(1L)).thenReturn(games());
    Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().firstName("John").lastName("Doe").build()));
    Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(User.builder().firstName("Mary").lastName("Jane").build()));

    var gameHistory = gameHistoryService.findGameHistory(1L);

    gameHistory.forEach(System.out::println);
  }

  List<Game> games() {
    return List.of(
        Game.forTesting(1L, 1L, 2L, 2L, 1L, GameStatus.FORFEITED),
        Game.forTesting(2L, 1L, 2L, 1L, 2L, GameStatus.WON_LOST),
        Game.forTesting(3L, 1L, 2L, null, null, GameStatus.DRAW),
        Game.forTesting(4L, 1L, 2L, null, null, GameStatus.IN_PROGRESS)
    );
  }
}