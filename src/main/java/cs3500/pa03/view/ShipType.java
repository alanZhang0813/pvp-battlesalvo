package cs3500.pa03.view;

/**
 * Enum to represent each possible state for a Ship
 */
public enum ShipType {
  CARRIER(6), BATTLESHIP(5), DESTROYER(4), SUBMARINE(3),
  SUNK(0), EMPTY(-1), HIT, MISS;

  private int size;

  ShipType() {
  }

  ShipType(int size) {
    this.size = size;
  }

  public int getSize() {
    return size;
  }

  /**
   * Returns a String representation of each ShipType for the board
   *
   * @return String
   */
  public String toString() {
    if (this.equals(EMPTY)) {
      return "_";
    } else if (this.equals(CARRIER)) {
      return "C";
    } else if (this.equals(BATTLESHIP)) {
      return "B";
    } else if (this.equals(DESTROYER)) {
      return "D";
    } else if (this.equals(SUBMARINE)) {
      return "S";
    } else if (this.equals(HIT)) {
      return "X";
    } else {
      return "O";
    }
  }
}
