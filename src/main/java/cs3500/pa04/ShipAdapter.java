package cs3500.pa04;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.view.Coord;
import cs3500.pa03.view.Direction;
import cs3500.pa03.view.Ship;

/**
 * The class that is used to convert my Ship implementation (List of coords, ShipType, Direction)
 * to one more conforming of the assignment
 */
public class ShipAdapter {
  private final Coord coord;
  private final int length;
  private final Direction direction;

  /**
   * Constructor that takes in my own Ship implementation
   *
   * @param myShip My Ship class
   */
  public ShipAdapter(Ship myShip) {
    this.coord = myShip.getLocation().get(0);
    this.length = myShip.getName().getSize();
    this.direction = myShip.getDirection();
  }

  /**
   * ShipAdapter constructor with each value
   *
   * @param coord Starting coordination
   * @param length Length of the ship
   * @param direction Direction of the Ship (HORIZONTAL, VERTICAL)
   */
  @JsonCreator
  public ShipAdapter(
      @JsonProperty("coord") Coord coord,
      @JsonProperty("length") int length,
      @JsonProperty("direction") Direction direction) {
    this.coord = coord;
    this.length = length;
    this.direction = direction;
  }

  public Coord getCoord() {
    return coord;
  }

  public int getLength() {
    return length;
  }

  public Direction getOrientation() {
    return direction;
  }
}
