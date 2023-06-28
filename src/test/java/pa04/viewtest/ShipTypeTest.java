package pa04.viewtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.ShipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Constructor for ShipType
 */
public class ShipTypeTest {
  private ShipType hitType;
  private ShipType missType;

  @BeforeEach
  void setup() {
    hitType = ShipType.HIT;
    missType = ShipType.MISS;
  }

  @Test
  void testToString() {
    assertEquals("X", hitType.toString());
    assertEquals("O", missType.toString());
  }
}
