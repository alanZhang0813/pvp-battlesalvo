package pa04.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.json.CoordJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the CoordJson record
 */
public class CoordJsonTest {
  CoordJson coordJson;

  @BeforeEach
  void setup() {
    coordJson =  new CoordJson(5, 10);
  }

  @Test
  void testCoordJson() {
    assertEquals(5, coordJson.x());
    assertEquals(10, coordJson.y());
  }
}
