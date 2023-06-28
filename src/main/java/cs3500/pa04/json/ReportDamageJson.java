package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.view.Coord;
import java.util.List;

/**
 * Json Object that represents the report-damage server request
 *
 * @param opponentShots The opponent's shots that hit the player (AI)'s board
 */
public record ReportDamageJson(
    @JsonProperty("coordinates") List<Coord> opponentShots
) {
}
