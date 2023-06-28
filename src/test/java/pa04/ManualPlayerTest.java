package pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.view.Coord;
import cs3500.pa03.view.GameResult;
import cs3500.pa03.view.Ship;
import cs3500.pa03.view.ShipType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for ManualPlayer
 */
public class ManualPlayerTest {
  private Board board;
  private ManualPlayer mp;
  private List<Ship> listOfShips;
  private Map<ShipType, Integer> specifications;
  private ByteArrayOutputStream outputStream;
  private PrintStream printStream;

  @BeforeEach
  void setup() {
    board = new Board();
    mp = new ManualPlayer(board);
    listOfShips = new ArrayList<>();
    specifications = new HashMap<>();
    outputStream = new ByteArrayOutputStream();
    printStream = new PrintStream(outputStream);
    System.setOut(printStream);
  }

  @Test
  void testPlayerSetup() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.BATTLESHIP, 2);
    specifications.put(ShipType.CARRIER, 2);
    specifications.put(ShipType.DESTROYER, 2);
    specifications.put(ShipType.SUBMARINE, 2);
    listOfShips = mp.setup(10, 10, specifications);
    assertEquals(8, listOfShips.size());
  }

  @Test
  void testName() {
    String name = mp.name();
    assertNull(name);
  }

  @Test
  void testTakeShots() {
    String input = "(1,2) (3,4) (5,6) (0,0)";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    List<Coord> expectedCoords = new ArrayList<>();
    expectedCoords.add(new Coord(1, 2));
    expectedCoords.add(new Coord(3, 4));
    mp.setup(10, 10, specifications);

    List<Coord> shotCoords = mp.takeShots();

    assertEquals(4, shotCoords.size());
  }

  @Test
  void testSuccessfulHitsNoHits() {
    List<Coord> shots = new ArrayList<>();
    mp.successfulHits(shots);
    assertEquals("\nYour opponent's shots all missed!", outputStream.toString());
  }

  @Test
  void testSuccessfulHitsWithHits() {
    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(1, 2));
    shots.add(new Coord(3, 4));
    mp.successfulHits(shots);
    assertEquals("\nYour opponent hit you at: \n(1,2)\n(3,4)\n", outputStream.toString());
  }

  @Test
  void testReportDamage() {
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    listOfShips = mp.setup(10, 10, specifications);
    List<Coord> opponentShots = new ArrayList<>();
    List<Coord> reportedDamage = mp.reportDamage(opponentShots);
    assertEquals(0, reportedDamage.size());
  }

  @Test
  void testEndGame() {
    mp.endGame(GameResult.WIN, "cuz i said so");
  }
}