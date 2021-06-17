package com.cobalt.tictactoe.game.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameJPARepository extends JpaRepository<GameEntity, Long> {

  @Query(
      "SELECT g FROM GameEntity g WHERE (g.firstPlayer = :player OR g.secondPlayer = :player) AND g.status IN ('IN_LOBBY', 'STARTED', 'IN_PROGRESS')")
  Optional<GameEntity> findByPlayerID(@Param("player") Long playerID);
}
