package pa04.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.ShipType;
import cs3500.pa04.json.SetupJson;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the SetupJson record
 */
public class SetupJsonTest {
  Map<ShipType, Integer> map = new HashMap<>();
  SetupJson setupJson;

  @BeforeEach
  void setup() {
    map.put(ShipType.CARRIER, 2);
    map.put(ShipType.DESTROYER, 2);
    map.put(ShipType.SUBMARINE, 2);
    map.put(ShipType.BATTLESHIP, 2);
    setupJson = new SetupJson(6, 6, map);
  }

  @Test
  void testSetupJson() {
    assertEquals(6, setupJson.height());
    assertEquals(6, setupJson.width());
    assertEquals(map, setupJson.fleetSpecs());
  }
}
