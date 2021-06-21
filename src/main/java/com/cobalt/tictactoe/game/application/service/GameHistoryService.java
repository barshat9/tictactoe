package com.cobalt.tictactoe.game.application.service;

import com.cobalt.tictactoe.common.annotations.UseCase;
import com.cobalt.tictactoe.game.application.port.in.GameCRUDUseCase;
import com.cobalt.tictactoe.game.application.port.in.GameHistory;
import com.cobalt.tictactoe.game.application.port.in.GameHistoryUseCase;
import com.cobalt.tictactoe.game.domain.Game;
import com.cobalt.tictactoe.game.domain.GameStatus;
import com.cobalt.tictactoe.user.domain.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class GameHistoryService implements GameHistoryUseCase {

  private final GameCRUDUseCase gameCRUDUseCase;

  private final UserRepository userRepository;

  @Override
  public List<GameHistory> findGameHistory(Long forPlayerID) {

    return findAllGamesForThisPlayer(gameCRUDUseCase)
        .andThen(buildGameHistory(userRepository, forPlayerID))
        .apply(forPlayerID);
  }

  Function<Long, List<? extends Game>> findAllGamesForThisPlayer(
      final GameCRUDUseCase gameCRUDUseCase) {
    return gameCRUDUseCase::findAllGamesByPlayer;
  }

  Function<List<? extends Game>, List<GameHistory>> buildGameHistory(
      UserRepository userRepository, Long forPlayerID) {
    return allGames ->
        allGames.stream()
            .map(
                game ->
                    findPlayers(userRepository)
                        .andThen(determineStatus(forPlayerID))
                        .andThen(buildGameHistory())
                        .apply(game))
            .collect(Collectors.toList());
  }

  Function<Game, Map<String, Object>> findPlayers(UserRepository userRepository) {
    return game -> {
      var fPlayer =
          userRepository
              .findById(game.getFirstPlayer())
              .orElseThrow(() -> new IllegalArgumentException("No User Found"));
      String firstPlayerName = fPlayer.fullName();
      String secondPlayerName = "";
      if (game.getSecondPlayer() != null) {
        var sPlayer =
            userRepository
                .findById(game.getSecondPlayer())
                .orElseThrow(() -> new IllegalArgumentException("No User Found"));
        secondPlayerName = sPlayer.fullName();
      }
      return Map.of("firstPlayer", firstPlayerName, "secondPlayer", secondPlayerName, "game", game);
    };
  }

  Function<Map<String, Object>, Map<String, Object>> determineStatus(Long forPlayerID) {
    log.info("Determining Status For Player {}", forPlayerID);
    return map -> {
      Game game = (Game) map.get("game");
      String status = "NA";
      if (game.getStatus() == GameStatus.DRAW
          || game.getStatus() == GameStatus.IN_PROGRESS
          || game.getStatus() == GameStatus.IN_LOBBY
          || game.getStatus() == GameStatus.STARTED) {
        status = game.getStatus().name();
      }
      if (game.getStatus() == GameStatus.FORFEITED || game.getStatus() == GameStatus.WON_LOST) {
        if (forPlayerID.equals(game.getWinnerPlayerID())) {
          status = "WON";
        } else {
          status = "LOST";
        }
      }
      return Map.of(
          "firstPlayer",
          map.get("firstPlayer"),
          "secondPlayer",
          map.get("secondPlayer"),
          "status",
          status,
          "game",
          game);
    };
  }

  Function<Map<String, Object>, GameHistory> buildGameHistory() {
    return map -> {
      Game game = (Game) map.get("game");
      String firstPlayer = (String) map.get("firstPlayer");
      String secondPlayer = (String) map.get("secondPlayer");
      String status = (String) map.get("status");
      return GameHistory.builder()
          .id(game.getId())
          .firstPlayerName(firstPlayer)
          .secondPlayerName(secondPlayer)
          .status(status)
          .startedDate(null) // TODO
          .gameTimeInSeconds(100) // TODO
          .build();
    };
  }
}
