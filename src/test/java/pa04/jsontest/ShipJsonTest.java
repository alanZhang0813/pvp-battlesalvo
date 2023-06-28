package pa04.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.Direction;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.ShipJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the ShipJson record
 */
public class ShipJsonTest {
  CoordJson coordJson;
  ShipJson shipJson;

  @BeforeEach
  void setup() {
    coordJson = new CoordJson(5, 10);
    shipJson = new ShipJson(coordJson, 10, Direction.HORIZONTAL);
  }

  @Test
  void testShipJson() {
    assertEquals(new CoordJson(5, 10), shipJson.coord());
    assertEquals(10, shipJson.length());
    assertEquals(Direction.HORIZONTAL, shipJson.direction());
  }
}
