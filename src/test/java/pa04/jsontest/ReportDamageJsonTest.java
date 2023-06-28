package pa04.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.Coord;
import cs3500.pa04.json.ReportDamageJson;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the ReportDamageJson record
 */
public class ReportDamageJsonTest {
  List<Coord> coordList;
  ReportDamageJson reportDamageJson;

  @BeforeEach
  void setup() {
    coordList = new ArrayList<>();
    reportDamageJson = new ReportDamageJson(coordList);
  }

  @Test
  void testReportDamage() {
    assertEquals(0, reportDamageJson.opponentShots().size());
  }
}
