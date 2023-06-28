package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json Object of the Coord class
 *
 * @param x The x-value of that coordinate
 * @param y The y-value of that coordinate
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y
) {
}
