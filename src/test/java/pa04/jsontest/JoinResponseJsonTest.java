package pa04.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.GameType;
import cs3500.pa04.json.JoinResponseJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the JoinResponseJson record
 */
public class JoinResponseJsonTest {
  JoinResponseJson joinResponseJson;

  @BeforeEach
  void setup() {
    joinResponseJson = new JoinResponseJson("alanZhang0813", GameType.SINGLE);
  }

  @Test
  void testJoin() {
    assertEquals("alanZhang0813", joinResponseJson.name());
    assertEquals(GameType.SINGLE, joinResponseJson.gameType());
  }
}
