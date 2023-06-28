package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.view.Direction;

/**
 * Record that represents a Json object for a Ship
 *
 * @param coord the starting CoordJson
 * @param length the length of the given ship
 * @param direction the direction of the ship, either horizontal or vertical
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") Direction direction
) {
}
