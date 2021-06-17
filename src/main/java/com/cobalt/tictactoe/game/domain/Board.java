package com.cobalt.tictactoe.game.domain;

public class Board {

  private final byte[][] cells = new byte[3][3];

  void placeMark(byte playerSymbol, int index) {
    var coordinates = getCoordinates(index);
    cells[coordinates.X][coordinates.Y] = playerSymbol;
  }

  byte getMarkAtIndex(int index) {
    var coordinates = getCoordinates(index);
    return cells[coordinates.X][coordinates.Y];
  }

  boolean isEmpty() {
    return countEmptyCells() == 9;
  }

  int countEmptyCells() {
    int totalEmptyCells = 0;
    for (byte[] rows : cells) {
      for (byte cell : rows) {
        if (cell == 0) {
          totalEmptyCells ++;
        }
      }
    }
    return totalEmptyCells;
  }

  static class Coordinates {
    int X;
    int Y;
    Coordinates(int X, int Y) {
      this.X = X;
      this.Y = Y;
    }
  }

  static Coordinates getCoordinates(int index) {

    switch (index) {
      case 0:
        return new Coordinates(0, 0);
      case 1:
        return new Coordinates(0, 1);
      case 2:
        return new Coordinates(0, 2);
      case 3:
        return new Coordinates(1, 0);
      case 4:
        return new Coordinates(1, 1);
      case 5:
        return new Coordinates(1, 2);
      case 6:
        return new Coordinates(2, 0);
      case 7:
        return new Coordinates(2, 1);
      case 8:
        return new Coordinates(2, 2);
      default:
        throw new IllegalArgumentException("Illegal Index " + index);
    }
  }
}
