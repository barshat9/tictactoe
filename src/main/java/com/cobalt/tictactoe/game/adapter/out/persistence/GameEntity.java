package com.cobalt.tictactoe.game.adapter.out.persistence;

import com.cobalt.tictactoe.game.domain.GameStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "games")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GameEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_player")
  private Long firstPlayer;

  @Column(name = "second_player")
  private Long secondPlayer;

  @Enumerated(EnumType.STRING)
  private GameStatus status;

  @Column(name ="winner")
  private Long winnerPlayerID;

  @Column(name = "loser")
  private Long loserPlayerID;

  private byte b0;

  private byte b1;

  private byte b2;

  private byte b3;

  private byte b4;

  private byte b5;

  private byte b6;

  private byte b7;

  private byte b8;
}
