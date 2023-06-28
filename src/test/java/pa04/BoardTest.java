package pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Board;
import cs3500.pa03.view.Coord;
import cs3500.pa03.view.Direction;
import cs3500.pa03.view.Ship;
import cs3500.pa03.view.ShipType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for the Board class
 */
public class BoardTest {
  private List<Ship> listOfShips;
  private Board board;
  private Ship ship1;
  private Ship ship2;
  private Ship ship3;
  private Ship ship4;

  @BeforeEach
  void setup() {
    listOfShips = new ArrayList<>();
    board = new Board();
    ship1 = new Ship(ShipType.CARRIER);
    ship1.setDirection(Direction.HORIZONTAL);
    listOfShips.add(ship1);
    ship2 = new Ship(ShipType.BATTLESHIP);
    listOfShips.add(ship2);
    ship3 = new Ship(ShipType.DESTROYER);
    listOfShips.add(ship3);
    ship4 = new Ship(ShipType.SUBMARINE);
    listOfShips.add(ship4);
    board.setListOfShips(listOfShips);
  }

  @Test
  void testInitBoard() {
    board.initBoard(6, 10);
    assertEquals(ShipType.EMPTY, board.getCoords()[0][0].getStatus());
  }

  @Test
  void testToString() {
    assertEquals("", board.toString());
    board.initBoard(3, 3);
    assertEquals("___\n___\n___\n", board.toString());
  }

  @Test
  void testPlaceShips() {
    board.initBoard(10, 10);
    board.placeShips(listOfShips);
    assertTrue(board.toString().contains("CCCCCC"));
  }

  @Test
  void testReportDamage() {
    board.initBoard(10, 10);
    Ship asubmarine = new Ship(ShipType.SUBMARINE);
    List<Ship> asubmarineandhope = new ArrayList<>();
    asubmarineandhope.add(asubmarine);
    board.setListOfShips(asubmarineandhope);

    List<Coord> shots = new ArrayList<>();
    Coord subCoord1 = new Coord(1, 2);
    subCoord1.setStatus(ShipType.SUBMARINE);
    Coord subCoord2 = new Coord(3, 4);
    subCoord2.setStatus(ShipType.SUBMARINE);
    Coord subCoord3 = new Coord(5, 6);
    subCoord3.setStatus(ShipType.SUBMARINE);

    shots.add(subCoord1);
    shots.add(subCoord2);
    shots.add(subCoord3);

    asubmarine.setLocation(shots);

    List<Coord> reportDamage = this.board.reportDamage(shots);
    assertEquals(new ArrayList<>(), reportDamage);
  }
}