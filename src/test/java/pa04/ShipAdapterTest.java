package pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.view.Coord;
import cs3500.pa03.view.Direction;
import cs3500.pa03.view.Ship;
import cs3500.pa03.view.ShipType;
import cs3500.pa04.ShipAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the ShipAdapter class
 */
public class ShipAdapterTest {
  Ship ship;
  ShipAdapter shipAdapter1;
  ShipAdapter shipAdapter2;

  @BeforeEach
  void setup() {
    ship = new Ship(ShipType.SUBMARINE);
    ship.setLocation(new ArrayList<>(Arrays.asList(new Coord(0, 0))));
    shipAdapter1 = new ShipAdapter(ship);
    shipAdapter2 = new ShipAdapter(new Coord(0, 0), 3, Direction.HORIZONTAL);
  }

  @Test
  void testGetters() {
    assertTrue(shipAdapter2.getCoord().equals(new Coord(0, 0)));
    assertEquals(3, shipAdapter2.getLength());
    assertEquals(Direction.HORIZONTAL, shipAdapter2.getOrientation());
  }
}
