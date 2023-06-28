package cs3500.pa03.model;

import cs3500.pa03.view.Coord;
import cs3500.pa03.view.Ship;
import cs3500.pa03.view.ShipType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that represents the manual/console/human player
 */
public class ManualPlayer extends AbstractPlayer {
  private final Board board;
  private final List<Coord> takenShots;

  public ManualPlayer(Board board) {
    this.board = board;
    this.takenShots = new ArrayList<>();
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> resultShips = super.setup(height, width, specifications);
    board.initBoard(height, width);
    board.placeShips(resultShips);
    return resultShips;
  }

  @Override
  public List<Coord> takeShots() {
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    ArrayList<Coord> shotCoords = new ArrayList<>();
    String shots = "";
    String[] shotsAtShips = shots.split(" ");
    try {
      while (shotsAtShips.length != board.getListOfShips().size()) {
        System.out.println("Number of ships: " + board.getListOfShips().size());
        shots = inputReader.readLine();
        shotsAtShips = shots.split(" ");
      }
      for (String coord : shotsAtShips) {
        if (coord.contains("(") && coord.contains(",") && coord.contains(")")) {
          String stringX = coord.substring(coord.indexOf("(") + 1, coord.indexOf(","));
          String stringY = coord.substring(coord.indexOf(",") + 1, coord.indexOf(")"));
          int x = Integer.parseInt(stringX.trim());
          int y = Integer.parseInt(stringY.trim());
          Coord shotCoord = new Coord(x, y);
          if (!takenShots.contains(shotCoord)) {
            shotCoords.add(shotCoord);
            takenShots.add(shotCoord);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return shotCoords;
  }

  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    return board.reportDamage(opponentShotsOnBoard);
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    Appendable output = new PrintStream(System.out);
    try {
      output.append("\n");
      if (shotsThatHitOpponentShips.isEmpty()) {
        output.append("Your opponent's shots all missed!");
      } else {
        output.append("Your opponent hit you at: \n");
        for (Coord coord : shotsThatHitOpponentShips) {
          output.append(coord.toString());
          output.append("\n");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}