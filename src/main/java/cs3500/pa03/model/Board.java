package cs3500.pa03.model;

import cs3500.pa03.view.Coord;
import cs3500.pa03.view.Direction;
import cs3500.pa03.view.Ship;
import cs3500.pa03.view.ShipType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Class to represent a Board object
 */
public class Board {
  private int height;
  private int width;
  private Coord[][] coords;
  private List<Ship> listOfShips;

  /**
   * Makes every spot in the board a new Coord with its associated r and c values,
   * as well as with the EMPTY status, so it prints out as a "_"
   */
  public void initBoard(int height, int width) {
    this.height = height;
    this.width = width;
    coords = new Coord[height][width];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        Coord currCoord = new Coord(c, r);
        currCoord.setStatus(ShipType.EMPTY);
        coords[r][c] = currCoord;
      }
    }
  }

  /**
   * Method that places the given ships in their randomized locations, ensuring no overlap
   *
   * @param listOfShips The list of ships from setup
   */
  public void placeShips(List<Ship> listOfShips) {
    this.listOfShips = listOfShips;
    for (Ship ship : listOfShips) {
      boolean emptyCoord = false;
      while (!emptyCoord) {
        Random rand = new Random();
        int randRow;
        int randCol;

        if (ship.getDirection().equals(Direction.HORIZONTAL)) {
          //checks if it is horizontal
          randRow = rand.nextInt(height);
          randCol = rand.nextInt(width - ship.getName().getSize() + 1);

          if (isCoordEmpty(randRow, randCol, ship)) {
            emptyCoord = true;
            for (int i = 0; i < ship.getName().getSize(); i++) {
              coords[randRow][randCol + i].setStatus(ship.getName());
              ship.addLocation(coords[randRow][randCol + i]);
            }
          }
        } else if (ship.getDirection().equals(Direction.VERTICAL)) {
          //since not horizontal, must be vertical
          randRow = rand.nextInt(height - ship.getName().getSize() + 1);
          randCol = rand.nextInt(width);

          if (isCoordEmpty(randRow, randCol, ship)) {
            emptyCoord = true;
            for (int i = 0; i < ship.getName().getSize(); i++) {
              coords[randRow + i][randCol].setStatus(ship.getName());
              ship.addLocation(coords[randRow + i][randCol]);
            }
          }
        }
      }
    }
  }

  private boolean isCoordEmpty(int rowNum, int colNum, Ship ship) {
    if (ship.getDirection().equals(Direction.VERTICAL)) {
      for (int r = 0; r < ship.getName().getSize(); r++) {
        if (!coords[rowNum + r][colNum].getStatus().equals(ShipType.EMPTY)) {
          return false;
        }
      }
    } else {
      for (int c = 0; c < ship.getName().getSize(); c++) {
        if (!coords[rowNum][colNum + c].getStatus().equals(ShipType.EMPTY)) {
          return false;
        }
      }
    }
    return coords[rowNum][colNum].getStatus().equals(ShipType.EMPTY);
  }

  /**
   * Returns a String representation of the Board's cells
   *
   * @return String
   */
  public String toString() {
    String result = "";
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        result = result.concat(coords[r][c].getStatus().toString());
      }
      result = result.concat("\n");
    }
    return result;
  }

  /**
   * Returns the list of coordinates on the board that match the shots from the opponent
   *
   * @param shots Shots fired by the opponent
   * @return The shots that hit ships
   */
  public List<Coord> reportDamage(List<Coord> shots) {
    List<Coord> hitShips = new ArrayList<>();
    for (Coord coord : shots) {
      int currX = coord.getX();
      int currY = coord.getY();
      if (coords[currY][currX].isShip()) {
        coords[currY][currX].setStatus(ShipType.HIT);
        hitShips.add(coord);
      } else {
        coords[currY][currX].setStatus(ShipType.MISS);
      }
    }
    Iterator<Ship> iterator = listOfShips.iterator();
    while (iterator.hasNext()) {
      Ship ship = iterator.next();
      if (ship.isSunken()) {
        iterator.remove();
      }
    }
    return hitShips;
  }

  public Coord[][] getCoords() {
    return this.coords;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public List<Ship> getListOfShips() {
    return listOfShips;
  }

  public void setListOfShips(List<Ship> listOfShips) {
    this.listOfShips = listOfShips;
  }
}