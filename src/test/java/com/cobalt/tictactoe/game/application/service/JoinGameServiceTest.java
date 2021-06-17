package com.cobalt.tictactoe.game.application.service;

import static org.junit.jupiter.api.Assertions.*;

import com.cobalt.tictactoe.game.application.port.in.GameCRUDUseCase;
import com.cobalt.tictactoe.game.application.port.in.JoinGameCommand;
import com.cobalt.tictactoe.game.application.port.out.FindInLobbyPort;
import com.cobalt.tictactoe.game.application.port.out.PutInLobbyPort;
import com.cobalt.tictactoe.game.domain.Game;
import com.cobalt.tictactoe.game.domain.GameBuilder;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(MockitoExtension.class)
class JoinGameServiceTest {


  @Mock
  private FindInLobbyPort findInLobbyPort;

  @Mock
  private GameCRUDUseCase gameCRUDUseCase;

  @Mock
  private PutInLobbyPort putInLobbyPort;

  private JoinGameService joinGameService;

  @BeforeEach
  public void init() {
    Objects.requireNonNull(findInLobbyPort);
    Objects.requireNonNull(gameCRUDUseCase);
    joinGameService = new JoinGameService(findInLobbyPort, gameCRUDUseCase, putInLobbyPort);
  }

  @Test
  void joinGame_GameNotFoundInLobby() {
    Mockito.when(findInLobbyPort.findAnyWaitingGame()).thenReturn(Optional.empty());
    byte[] b = new byte[3];
    Game createdGame = new GameBuilder.ExistingGameBuilder().id(100L).withSymbolPositions(b).build();
    Mockito.when(gameCRUDUseCase.create(Mockito.any(Game.class))).thenReturn(createdGame);
    var game = joinGameService.joinGame(new JoinGameCommand(1L));
    Assertions.assertEquals(100L, game.getId());
  }

  @Test
  void joinGame_GameFoundInLobby() {
    Mockito.when(findInLobbyPort.findAnyWaitingGame()).thenReturn(Optional.of(101L));
    byte[] b = new byte[3];
    Game createdGame = new GameBuilder.ExistingGameBuilder().id(9099L).withSymbolPositions(b).build();
    Mockito.when(gameCRUDUseCase.retrieve(Mockito.any(Long.class))).thenReturn(createdGame);
    var game = joinGameService.joinGame(new JoinGameCommand(1L));
    Assertions.assertEquals(9099L, game.getId());
  }
}