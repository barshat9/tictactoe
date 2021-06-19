package com.cobalt.tictactoe.game.domain;

import lombok.Getter;
import lombok.Setter;

public class Game {

  @Getter private Long id;

  private Board board;

  @Getter private Long firstPlayer;

  @Getter @Setter private Long secondPlayer;

  private GameStatus status;

  @Getter @Setter private Long winnerPlayerID;

  @Getter @Setter private Long loserPlayerID;

  Game(Long firstPlayer) {
    this.firstPlayer = firstPlayer;
    board = new Board();
    status = GameStatus.IN_LOBBY;
  }

  Game(Long id, Long firstPlayer, Long secondPlayer, Board board, GameStatus status) {
    this.id = id;
    this.firstPlayer = firstPlayer;
    this.secondPlayer = secondPlayer;
    this.board = board;
    this.status = status;
  }

  public static Game forTesting(Long id, Long firstPlayer, Long secondPlayer, Long loserPlayerID, Long winnerPlayerID, GameStatus status) {
    var game = new Game(id, firstPlayer, secondPlayer, null, status);
    game.setWinnerPlayerID(winnerPlayerID);
    game.setLoserPlayerID(loserPlayerID);
    return game;
  }

  public GameStatus makeMove(Long playerID, int index) {
    byte playerSymbol = symbolFor(playerID);
    board.placeMark(playerSymbol, index);
    status = doGetGameStatus();
    if (status == GameStatus.WON_LOST) {
      winnerPlayerID = playerID;
      loserPlayerID = playerID.equals(firstPlayer) ? secondPlayer : firstPlayer;
    }
    return status;
  }

  public GameStatus getStatus() {
    return status;
  }

  public void start() {
    this.status = GameStatus.STARTED;
  }

  public void inProgress() {
    this.status = GameStatus.IN_PROGRESS;
  }

  public void quit() {
    this.status = GameStatus.FORFEITED;
  }

  public static byte symbolFor(Long playerID) {
    return (byte) ((playerID % 2) + 1);
  }

  public byte getMarkAtIndex(int index) {
    return board.getMarkAtIndex(index);
  }

  GameStatus doGetGameStatus() {
    if (isWinLost()) {
      return GameStatus.WON_LOST;
    }
    if (isDraw()) {
      return GameStatus.DRAW;
    }
    return GameStatus.IN_PROGRESS;
  }

  private boolean isWinLost() {
    if (board.isEmpty()) {
      throw new IllegalStateException("Should Not Decide Win Lost, Board Is Empty");
    }
    return checkWinLostAlongRows() || checkWinLostAlongColumns() || checkWinLostAlongDiagonals();
  }

  private boolean isDraw() {
    return board.countEmptyCells() == 1;
  }

  private boolean checkWinLostAlongRows() {
    boolean r0 =
        (board.getMarkAtIndex(0) != 0)
            && (board.getMarkAtIndex(0) == board.getMarkAtIndex(1)
                && board.getMarkAtIndex(1) == board.getMarkAtIndex(2));
    boolean r1 =
        (board.getMarkAtIndex(3) != 0)
            && (board.getMarkAtIndex(3) == board.getMarkAtIndex(4)
                && board.getMarkAtIndex(4) == board.getMarkAtIndex(5));
    boolean r2 =
        (board.getMarkAtIndex(6) != 0)
            && (board.getMarkAtIndex(6) == board.getMarkAtIndex(7)
                && board.getMarkAtIndex(4) == board.getMarkAtIndex(8));
    return r0 || r1 || r2;
  }

  private boolean checkWinLostAlongColumns() {
    boolean c0 =
        (board.getMarkAtIndex(0) != 0)
            && (board.getMarkAtIndex(0) == board.getMarkAtIndex(3)
                && board.getMarkAtIndex(3) == board.getMarkAtIndex(6));
    boolean c1 =
        (board.getMarkAtIndex(1) != 0)
            && (board.getMarkAtIndex(1) == board.getMarkAtIndex(4)
                && board.getMarkAtIndex(4) == board.getMarkAtIndex(7));
    boolean c2 =
        (board.getMarkAtIndex(2) != 0)
            && (board.getMarkAtIndex(2) == board.getMarkAtIndex(5)
                && board.getMarkAtIndex(5) == board.getMarkAtIndex(8));
    return c0 || c1 || c2;
  }

  private boolean checkWinLostAlongDiagonals() {
    boolean d1 =
        (board.getMarkAtIndex(0) != 0)
            && (board.getMarkAtIndex(0) == board.getMarkAtIndex(4)
                && board.getMarkAtIndex(4) == board.getMarkAtIndex(8));
    boolean d2 =
        (board.getMarkAtIndex(2) != 0)
            && (board.getMarkAtIndex(2) == board.getMarkAtIndex(4)
                && board.getMarkAtIndex(4) == board.getMarkAtIndex(6));
    return d1 || d2;
  }
}
