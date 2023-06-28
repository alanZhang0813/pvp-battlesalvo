package pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.view.Coord;
import cs3500.pa03.view.GameResult;
import cs3500.pa03.view.Ship;
import cs3500.pa03.view.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing the AiPlayer
 */
public class AiPlayerTest {
  private Board board;
  private AiPlayer aiPlayer;
  private Map<ShipType, Integer> specifications;
  private List<Ship> listOfShips;
  private ByteArrayOutputStream outputStream;
  private PrintStream printStream;

  @BeforeEach
  void setup() {
    board = new Board();
    board.setWidth(5);
    board.setHeight(5);
    aiPlayer = new AiPlayer(board);
    specifications = new HashMap<>();
    listOfShips = new ArrayList<>();
    outputStream = new ByteArrayOutputStream();
    printStream = new PrintStream(outputStream);
    System.setOut(printStream);
  }

  @Test
  void testName() {
    String name = aiPlayer.name();
    assertEquals("alanZhang0813", name);
  }

  @Test
  void testPlayerSetup() {
    specifications.put(ShipType.BATTLESHIP, 2);
    specifications.put(ShipType.CARRIER, 2);
    specifications.put(ShipType.DESTROYER, 2);
    specifications.put(ShipType.SUBMARINE, 2);

    listOfShips = aiPlayer.setup(10, 10, specifications);
    assertEquals(8, listOfShips.size());
  }

  @Test
  void testTakeShots() {
    List<Ship> listOfShips = new ArrayList<>();
    listOfShips.add(new Ship(ShipType.CARRIER));
    listOfShips.add(new Ship(ShipType.CARRIER));
    board.setListOfShips(listOfShips);
    List<Coord> shotCoords = aiPlayer.takeShots();
    assertEquals(2, shotCoords.size());
  }

  @Test
  void testReportDamage() {
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    listOfShips = aiPlayer.setup(10, 10, specifications);
    List<Coord> opponentShots = new ArrayList<>();
    List<Coord> reportedDamage = aiPlayer.reportDamage(opponentShots);
    assertEquals(0, reportedDamage.size());
  }

  @Test
  void testSuccessfulHitsNoHits() {
    List<Coord> shots = new ArrayList<>();
    aiPlayer.successfulHits(shots);
    assertEquals("\nYour shots all missed!\n", outputStream.toString());
  }

  @Test
  void testSuccessfulHitsWithHits() {
    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(1, 2));
    shots.add(new Coord(3, 4));
    aiPlayer.successfulHits(shots);
    assertEquals("\nYou hit your opponent at: \n(1,2)\n(3,4)\n\n", outputStream.toString());
  }

  @Test
  void testEndGameWin() {
    aiPlayer.endGame(GameResult.WIN, "");
    assertEquals("You won the game, because \n", outputStream.toString());
  }

  @Test
  void testEndGameDraw() {
    aiPlayer.endGame(GameResult.DRAW, "idk");
    assertEquals("The game was a draw, because idk\n", outputStream.toString());
  }

  @Test
  void testEndGameLose() {
    aiPlayer.endGame(GameResult.LOSE, "");
    assertEquals("You lost the game, because \n", outputStream.toString());
  }
}