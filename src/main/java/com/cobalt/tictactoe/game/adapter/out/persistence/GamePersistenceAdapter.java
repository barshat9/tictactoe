package com.cobalt.tictactoe.game.adapter.out.persistence;

import com.cobalt.tictactoe.game.application.port.out.GameCRUDPort;
import com.cobalt.tictactoe.game.domain.Game;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GamePersistenceAdapter implements GameCRUDPort {

  private final GameJPARepository gameJPARepository;

  private final GameEntityModelMapper mapper = new GameEntityModelMapper();

  @Override
  public Game findByID(Long gameID) {
    var gameEntity =
        gameJPARepository
            .findById(gameID)
            .orElseThrow(() -> new IllegalStateException("No Game Found " + gameID));
    return mapper.mapToSource(gameEntity);
  }

  @Override
  public Game save(Game game) {
    var gameEntity = mapper.mapToTarget(game);
    var persistedGame = gameJPARepository.save(gameEntity);
    return mapper.mapToSource(persistedGame);
  }

  @Override
  public Optional<Game> findByPlayerID(Long playerID) {
    return gameJPARepository.findByPlayerID(playerID).map(mapper::mapToSource);
  }

  @Override
  public List<Game> findAllGamesByPlayerID(Long playerID) {
    return mapper.mapToSource(gameJPARepository.findAllGamesByPlayerID(playerID));
  }
}
