package cs3500.pa03.model;

import cs3500.pa03.view.Coord;
import cs3500.pa03.view.Ship;
import cs3500.pa03.view.ShipType;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class for the AI Player
 */
public class AiPlayer extends AbstractPlayer {
  private final Board board;
  private final List<Coord> takenShots;

  public AiPlayer(Board board) {
    this.board = board;
    this.takenShots = new ArrayList<>();
  }

  @Override
  public String name() {
    return "pa04-panickedscreaming";
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
    List<Coord> roundShots = new ArrayList<>();
    while (roundShots.size() < board.getListOfShips().size()) {
      Random rand = new Random();
      int randRow = rand.nextInt(board.getHeight());
      int randCol = rand.nextInt(board.getWidth());
      Coord newCoord = new Coord(randCol, randRow);
      if (!this.coordContains(takenShots, newCoord)) {
        roundShots.add(newCoord);
        takenShots.add(newCoord);
      }
    }
    return roundShots;
  }

  private boolean coordContains(List<Coord> aiShotCoords, Coord newCoord) {
    for (Coord coord : aiShotCoords) {
      if (coord.equals(newCoord)) {
        return true;
      }
    }
    return false;
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
        output.append("Your shots all missed!");
      } else {
        output.append("You hit your opponent at: \n");
        for (Coord coord : shotsThatHitOpponentShips) {
          output.append(coord.toString());
          output.append("\n");
        }
      }
      output.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}