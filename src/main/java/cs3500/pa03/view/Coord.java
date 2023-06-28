package cs3500.pa03.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represent a Coord object, a 2D array of which makes the Board
 */
public class Coord {
  private final int xcoord;
  private final int ycoord;
  private ShipType status;

  @JsonCreator
  public Coord(@JsonProperty("x") int xcoord, @JsonProperty("y") int ycoord) {
    this.xcoord = xcoord;
    this.ycoord = ycoord;
  }

  /**
   * Returns if this Coord is occupied by a ship
   *
   * @return boolean
   */
  public boolean isShip() {
    return status.equals(ShipType.DESTROYER)
        || status.equals(ShipType.CARRIER)
        || status.equals(ShipType.BATTLESHIP)
        || status.equals(ShipType.SUBMARINE);
  }

  public boolean equals(Coord other) {
    return this.getX() == other.getX() && this.getY() == other.getY();
  }

  public ShipType getStatus() {
    return status;
  }

  public void setStatus(ShipType status) {
    this.status = status;
  }

  public int getX() {
    return xcoord;
  }

  public int getY() {
    return ycoord;
  }

  public String toString() {
    return "(" + this.xcoord + "," + this.ycoord + ")";
  }
}
