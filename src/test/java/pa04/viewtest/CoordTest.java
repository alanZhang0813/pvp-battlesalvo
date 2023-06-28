package pa04.viewtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.view.Coord;
import cs3500.pa03.view.ShipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the Coord class
 */
public class CoordTest {
  private Coord coord;

  @BeforeEach
  void setup() {
    coord = new Coord(0, 0);
  }

  @Test
  void testIsShip() {
    coord.setStatus(ShipType.CARRIER);
    assertTrue(coord.isShip());
    coord.setStatus(ShipType.BATTLESHIP);
    assertTrue(coord.isShip());
    coord.setStatus(ShipType.DESTROYER);
    assertTrue(coord.isShip());
    coord.setStatus(ShipType.SUBMARINE);
    assertTrue(coord.isShip());
    coord.setStatus(ShipType.HIT);
    assertFalse(coord.isShip());
  }

  @Test
  void testEquals() {
    Coord coord1 = new Coord(0, 0);
    assertTrue(coord.equals(coord1));
    coord1 = new Coord(1, 0);
    assertFalse(coord.equals(coord1));
    coord1 = new Coord(0, 1);
    assertFalse(coord.equals(coord1));
  }

  @Test
  void testToString() {
    assertEquals("(0,0)", coord.toString());
  }

  @Test
  void testGetStatus() {
    coord.setStatus(ShipType.HIT);
    assertEquals(ShipType.HIT, coord.getStatus());
  }
}
