package pa04.viewtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.view.Coord;
import cs3500.pa03.view.Ship;
import cs3500.pa03.view.ShipType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the Ship class
 */
public class ShipTest {
  private List<Coord> location;
  private Ship ship;

  @BeforeEach
  void setup() {
    location = new ArrayList<>();
    ship = new Ship(ShipType.CARRIER);
    ship.setLocation(location);
  }

  @Test
  void testIsSunken() {
    Coord sunkLocation = new Coord(0, 0);
    sunkLocation.setStatus(ShipType.HIT);
    location.add(sunkLocation);
    assertTrue(ship.isSunken());
    Coord unsunken = new Coord(0, 1);
    unsunken.setStatus(ShipType.CARRIER);
    location.add(unsunken);
    assertFalse(ship.isSunken());
  }

  @Test
  void testGetLocation() {
    assertEquals(0, ship.getLocation().size());
  }
}
