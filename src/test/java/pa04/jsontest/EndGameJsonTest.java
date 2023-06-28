package pa04.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.GameResult;
import cs3500.pa04.json.EndGameJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testing the EndGameJson record
 */
public class EndGameJsonTest {
  EndGameJson endGameJson;

  @BeforeEach
  void setup() {
    endGameJson = new EndGameJson(GameResult.DRAW, "because i said so");
  }

  @Test
  void testEndGameJson() {
    assertEquals(GameResult.DRAW, endGameJson.gameResult());
    assertEquals("because i said so", endGameJson.reason());
  }
}
