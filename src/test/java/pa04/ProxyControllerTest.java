package pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.GameResult;
import cs3500.pa03.view.Ship;
import cs3500.pa03.view.ShipType;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.proxy.ProxyController;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for ProxyController
 */
public class ProxyControllerTest {
  private Player aiPlayer;
  private Board board;
  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private EndGameJson endGame;
  private JsonNode endGameNode;
  private MessageJson endGameMessage;
  private JsonNode responseNode;
  private static final JsonNode EMPTY = JsonNodeFactory.instance.objectNode();

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  void setup() {
    board = new Board();
    board.initBoard(10, 10);
    aiPlayer = new AiPlayer(board);
    this.testLog = new ByteArrayOutputStream(2048);
    endGame = new EndGameJson(GameResult.DRAW, "Test over");
    endGameNode = JsonUtils.serializeRecord(endGame);
    endGameMessage = new MessageJson("end-game", endGameNode);
    responseNode = JsonUtils.serializeRecord(endGameMessage);
  }

  @Test
  void testHandleJoin() {
    MessageJson sampleMessage = new MessageJson("join", EMPTY);
    JsonNode sampleJson = JsonUtils.serializeRecord(sampleMessage);

    List<String> inputMessages = List.of(sampleJson.toString(), responseNode.toString());
    Mocket socket = new Mocket(this.testLog, inputMessages);

    try {
      this.controller = new ProxyController(socket, aiPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();

    assertEquals("{\"method-name\":\"join\",\"arguments\":"
        + "{\"name\":\"alanZhang0813\",\"game-type\":\"SINGLE\"}}\n"
        + "{\"method-name\":\"end-game\",\"arguments\":\"void\"}\n", testLog.toString());
  }

  @Test
  void testSetup() {
    int width = 10;
    int height = 10;
    Map<ShipType, Integer> fleetSpecs = new HashMap<>();
    fleetSpecs.put(ShipType.CARRIER, 1);
    fleetSpecs.put(ShipType.BATTLESHIP, 1);
    fleetSpecs.put(ShipType.DESTROYER, 1);
    fleetSpecs.put(ShipType.SUBMARINE, 1);
    SetupJson sampleSetup = new SetupJson(width, height, fleetSpecs);
    JsonNode sampleNode = createSampleMessage("setup", sampleSetup);

    Mocket socket = new Mocket(this.testLog, List.of(sampleNode.toString(),
        responseNode.toString()));

    try {
      this.controller = new ProxyController(socket, aiPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();

    assertTrue(testLog.toString().contains("{\"method-name\":\"setup\",\"arguments\":{\"fleet\":"));
  }

  @Test
  void testTakeShots() {
    board.setListOfShips(List.of(new Ship(ShipType.CARRIER), new Ship(ShipType.BATTLESHIP)));

    MessageJson sampleMessage = new MessageJson("take-shots", EMPTY);
    JsonNode sampleJson = JsonUtils.serializeRecord(sampleMessage);

    List<String> inputMessages = List.of(sampleJson.toString(), responseNode.toString());
    Mocket socket = new Mocket(this.testLog, inputMessages);

    try {
      this.controller = new ProxyController(socket, aiPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();

    assertTrue(testLog.toString().contains("{\"method-name\":\"take-shots\","
        + "\"arguments\":{\"coordinates\":"));
    assertTrue(testLog.toString().contains("{\"method-name\":\"end-game\","
        + "\"arguments\":\"void\"}"));
  }

  @Test
  void testReportDamage() {
    List<CoordJson> shotCoords = List.of(new CoordJson(0, 0), new CoordJson(1, 0));
    List<Ship> ships = new ArrayList<>(List.of(new Ship(ShipType.CARRIER),
        new Ship(ShipType.BATTLESHIP)));
    board.setListOfShips(ships);
    CoordinatesJson coordinatesJson = new CoordinatesJson(shotCoords);
    JsonNode reportDamageNode = JsonUtils.serializeRecord(coordinatesJson);

    MessageJson sampleMessage = new MessageJson("report-damage", reportDamageNode);
    JsonNode sampleJson = JsonUtils.serializeRecord(sampleMessage);

    List<String> inputMessages = List.of(sampleJson.toString(), responseNode.toString());
    Mocket socket = new Mocket(this.testLog, inputMessages);

    try {
      this.controller = new ProxyController(socket, aiPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();
    assertEquals("{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\":[]}}\n"
        + "{\"method-name\":\"end-game\",\"arguments\":\"void\"}\n", testLog.toString());
  }

  @Test
  void testSuccessfulHits() {
    List<CoordJson> coordinates = List.of(new CoordJson(0, 0), new CoordJson(1, 0));
    CoordinatesJson coordinatesJson = new CoordinatesJson(coordinates);

    JsonNode sampleNode = createSampleMessage("successful-hits", coordinatesJson);

    List<String> inputMessages = List.of(sampleNode.toString(), responseNode.toString());
    Mocket socket = new Mocket(this.testLog, inputMessages);

    try {
      this.controller = new ProxyController(socket, aiPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();
    assertEquals("{\"method-name\":\"successful-hits\",\"arguments\":\"void\"}\n"
        + "{\"method-name\":\"end-game\",\"arguments\":\"void\"}\n", testLog.toString());
  }

  @Test
  void testElse() {
    MessageJson sampleMessage = new MessageJson("nothing", EMPTY);
    JsonNode sampleNode = createSampleMessage("hits", sampleMessage);

    List<String> inputMessages = List.of(sampleNode.toString(), responseNode.toString());
    Mocket socket = new Mocket(this.testLog, inputMessages);

    try {
      this.controller = new ProxyController(socket, aiPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    try {
      this.controller.run();
    } catch (IllegalArgumentException e) {
      assertEquals("No such message name", e.getMessage());
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson = new MessageJson(messageName,
        JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}
