package cs3500.pa04.proxy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.Coord;
import cs3500.pa03.view.GameResult;
import cs3500.pa03.view.GameType;
import cs3500.pa03.view.Ship;
import cs3500.pa03.view.ShipType;
import cs3500.pa04.ShipAdapter;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinResponseJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.ShipJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Proxy class for Controller, to run with Server connection
 */
public class ProxyController {
  private final Socket server;
  private final Player aiPlayer;
  private final ObjectMapper mapper = new ObjectMapper();
  private final InputStream input;
  private final PrintStream output;
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Constructor for ProxyController
   *
   * @param server Socket server
   * @param aiPlayer The player object, here an AI
   * @throws IOException if socket is closed
   */
  public ProxyController(Socket server, Player aiPlayer)
      throws IOException {
    this.server = server;
    this.aiPlayer = aiPlayer;
    this.input = server.getInputStream();
    this.output = new PrintStream(server.getOutputStream());
  }

  /**
   * Class that parses and runs the interaction between server and client
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.input);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not parse");
    }
  }

  private void delegateMessage(MessageJson message) {
    String name = message.methodName();
    JsonNode arguments = message.arguments();
    //the actions that take in no parameters have empty parameter handle helpers
    if (name.equals("join")) {
      handleJoin();
    } else if (name.equals("setup")) {
      handleSetup(arguments);
    } else if (name.equals("take-shots")) {
      handleTakeShots();
    } else if (name.equals("report-damage")) {
      handleReportDamage(arguments);
    } else if (name.equals("successful-hits")) {
      handleSuccessfulHits();
    } else if (name.equals("end-game")) {
      handleEndGame(arguments);
    } else {
      throw new IllegalArgumentException("No such message name");
    }
  }

  private void serializeAndPrint(MessageJson messageJson) {
    JsonNode response = JsonUtils.serializeRecord(messageJson);
    this.output.println(response);
  }

  private void handleJoin() {
    String name = aiPlayer.name();

    JoinResponseJson response = new JoinResponseJson(name, GameType.SINGLE);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson messageResponse = new MessageJson("join", jsonResponse);
    serializeAndPrint(messageResponse);
  }

  private void handleSetup(JsonNode arguments) {
    SetupJson setupArgs = this.mapper.convertValue(arguments, SetupJson.class);

    int width = setupArgs.width();
    int height = setupArgs.height();
    Map<ShipType, Integer> fleetSpecs = setupArgs.fleetSpecs();

    List<Ship> fleet = aiPlayer.setup(height, width, fleetSpecs);
    List<ShipAdapter> adaptedFleet = this.adaptShips(fleet);
    List<ShipJson> fleetJson = this.makeJson(adaptedFleet);

    FleetJson response = new FleetJson(fleetJson);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson messageResponse = new MessageJson("setup", jsonResponse);
    serializeAndPrint(messageResponse);
  }

  private List<ShipAdapter> adaptShips(List<Ship> fleet) {
    List<ShipAdapter> adaptedFleet = new ArrayList<>();
    for (Ship ship : fleet) {
      ShipAdapter shipAdapter = new ShipAdapter(ship);
      adaptedFleet.add(shipAdapter);
    }
    return adaptedFleet;
  }

  private List<ShipJson> makeJson(List<ShipAdapter> adaptedShips) {
    List<ShipJson> fleetJson = new ArrayList<>();
    for (ShipAdapter ship : adaptedShips) {
      CoordJson coordJson = new CoordJson(ship.getCoord().getX(), ship.getCoord().getY());
      ShipJson shipJson = new ShipJson(coordJson, ship.getLength(), ship.getOrientation());
      fleetJson.add(shipJson);
    }
    return fleetJson;
  }

  private void handleTakeShots() {
    List<Coord> aiShots = aiPlayer.takeShots();
    List<CoordJson> aiShotsJson = this.adaptCoords(aiShots);
    CoordinatesJson shotsJson = new CoordinatesJson(aiShotsJson);

    JsonNode jsonResponse = JsonUtils.serializeRecord(shotsJson);
    MessageJson messageResponse = new MessageJson("take-shots", jsonResponse);
    serializeAndPrint(messageResponse);
  }

  private void handleReportDamage(JsonNode arguments) {
    ReportDamageJson reportDamageArgs = this.mapper.convertValue(arguments, ReportDamageJson.class);
    List<Coord> opponentShots = reportDamageArgs.opponentShots();
    List<Coord> reportDamageShots = aiPlayer.reportDamage(opponentShots);
    List<CoordJson> reportDamageShotsJson = this.adaptCoords(reportDamageShots);
    CoordinatesJson reportDamageJson = new CoordinatesJson(reportDamageShotsJson);

    JsonNode jsonResponse = JsonUtils.serializeRecord(reportDamageJson);
    MessageJson messageResponse = new MessageJson("report-damage", jsonResponse);
    serializeAndPrint(messageResponse);
  }

  private List<CoordJson> adaptCoords(List<Coord> coordShots) {
    List<CoordJson> aiShotsJson = new ArrayList<>();
    for (Coord coord : coordShots) {
      CoordJson coordJson = new CoordJson(coord.getX(), coord.getY());
      aiShotsJson.add(coordJson);
    }
    return aiShotsJson;
  }

  private void handleSuccessfulHits() {
    MessageJson messageJson = new MessageJson("successful-hits", VOID_RESPONSE);
    serializeAndPrint(messageJson);
  }

  private void handleEndGame(JsonNode arguments) {
    EndGameJson endGameArgs = this.mapper.convertValue(arguments, EndGameJson.class);
    GameResult gameResult = endGameArgs.gameResult();
    String reason = endGameArgs.reason();
    aiPlayer.endGame(gameResult, reason);
    MessageJson messageJson = new MessageJson("end-game", VOID_RESPONSE);
    serializeAndPrint(messageJson);
    try {
      server.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
