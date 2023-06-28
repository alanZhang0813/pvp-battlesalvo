package pa04.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.ShipJson;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the FleetJson record
 */
public class FleetJsonTest {
  List<ShipJson> fleet;
  FleetJson fleetJson;

  @BeforeEach
  void setup() {
    fleet = new ArrayList<>();
    fleetJson = new FleetJson(fleet);
  }

  @Test
  void testFleetJson() {
    assertEquals(0, fleetJson.fleet().size());
  }
}
