package com.cobalt.tictactoe.game.domain;

public class GameBuilder {

  public static class NewGameBuilder {

    private Long firstPlayer;

    public NewGameBuilder forPlayer(Long playerID) {
      this.firstPlayer = playerID;
      return this;
    }

    public Game build() {
      return new Game(firstPlayer);
    }
  }

  public static class ExistingGameBuilder {

    private Long id;

    private Long firstPlayer;

    private Long secondPlayer;

    private GameStatus status;

    private byte[] symbolPositions;

    public ExistingGameBuilder id(Long id) {
      this.id = id;
      return this;
    }

    public ExistingGameBuilder firstPlayer(Long firstPlayer) {
      this.firstPlayer = firstPlayer;
      return this;
    }

    public ExistingGameBuilder secondPlayer(Long secondPlayer) {
      this.secondPlayer = secondPlayer;
      return this;
    }

    public ExistingGameBuilder status(GameStatus status) {
      this.status = status;
      return this;
    }

    public ExistingGameBuilder withSymbolPositions(byte[] symbolPositions) {
      this.symbolPositions = symbolPositions;
      return this;
    }

    public Game build() {
      Board board = new Board();
      int position = 0;
      for (byte symbol : symbolPositions) {
        board.placeMark(symbol, position);
        position++;
      }
      return new Game(id, firstPlayer, secondPlayer, board, status);
    }
  }

  public NewGameBuilder newGameBuilder() {
    return new NewGameBuilder();
  }

  public ExistingGameBuilder existingGameBuilder() {
    return new ExistingGameBuilder();
  }
}
