package pa04.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.CoordinatesJson;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the CoordinatesJson record
 */
public class CoordinatesJsonTest {
  List<CoordJson> shots;
  CoordinatesJson coordinatesJson;

  @BeforeEach
  void setup() {
    shots = new ArrayList<>();
    coordinatesJson = new CoordinatesJson(shots);
  }

  @Test
  void testCoordinatesJson() {
    assertEquals(0, coordinatesJson.shots().size());
    shots.add(new CoordJson(1, 2));
    assertEquals(1, coordinatesJson.shots().size());
  }
}
