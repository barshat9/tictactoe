package com.cobalt.tictactoe.game.adapter.out.persistence;

import com.cobalt.tictactoe.common.mapper.ModelMapper;
import com.cobalt.tictactoe.game.domain.Game;
import com.cobalt.tictactoe.game.domain.GameBuilder;
import java.util.function.Function;

public class GameEntityModelMapper extends ModelMapper<Game, GameEntity> {

  public GameEntityModelMapper() {
    super(toEntity(), toDomain());
  }

  static Function<Game, GameEntity> toEntity() {
    return (domain) ->
        GameEntity.builder()
            .id(domain.getId())
            .firstPlayer(domain.getFirstPlayer())
            .secondPlayer(domain.getSecondPlayer())
            .winnerPlayerID(domain.getWinnerPlayerID())
            .loserPlayerID(domain.getLoserPlayerID())
            .status(domain.getStatus())
            .b0(domain.getMarkAtIndex(0))
            .b1(domain.getMarkAtIndex(1))
            .b2(domain.getMarkAtIndex(2))
            .b3(domain.getMarkAtIndex(3))
            .b4(domain.getMarkAtIndex(4))
            .b5(domain.getMarkAtIndex(5))
            .b6(domain.getMarkAtIndex(6))
            .b7(domain.getMarkAtIndex(7))
            .b8(domain.getMarkAtIndex(8))
            .build();
  }

  static Function<GameEntity, Game> toDomain() {
    return (entity) ->
        new GameBuilder.ExistingGameBuilder()
            .id(entity.getId())
            .firstPlayer(entity.getFirstPlayer())
            .secondPlayer(entity.getSecondPlayer())
            .withWinnerPlayerID(entity.getWinnerPlayerID())
            .status(entity.getStatus())
            .withSymbolPositions(
                new byte[] {
                  entity.getB0(),
                  entity.getB1(),
                  entity.getB2(),
                  entity.getB3(),
                  entity.getB4(),
                  entity.getB5(),
                  entity.getB6(),
                  entity.getB7(),
                  entity.getB8()
                })
            .build();
  }
}
