package cs3500.pa03.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to represent a Ship object
 */
public class Ship {
  private final ShipType name;
  private List<Coord> location;
  private Direction direction;

  /**
   * Constructor for Ship that only takes in a ShipType
   *
   * @param name A ShipType that each Ship has, one of the types of ships
   */
  public Ship(ShipType name) {
    this.name = name;
    this.direction = randOrientation();
    this.location = new ArrayList<>();
  }

  /**
   * Each ship should know if it is sunken
   * info used to then decide which ships to remove from the list
   *
   * @return true if every spot is HIT
   */
  public boolean isSunken() {
    for (Coord coord : location) {
      if (!coord.getStatus().equals(ShipType.HIT)) {
        return false;
      }
    }
    return true;
  }

  public void addLocation(Coord newLocation) {
    this.location.add(newLocation);
  }

  public List<Coord> getLocation() {
    return location;
  }

  public void setLocation(List<Coord> location) {
    this.location = location;
  }

  public ShipType getName() {
    return name;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  private Direction randOrientation() {
    Random rand = new Random();
    int randInt = rand.nextInt(2);
    if (randInt == 0) {
      return Direction.HORIZONTAL;
    } else {
      return Direction.VERTICAL;
    }
  }
}