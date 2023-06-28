package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.view.ShipType;
import java.util.Map;

/**
 * Json Object to represent the input format of the Setup message
 *
 * @param width The width of the board
 * @param height The height of the board
 * @param fleetSpecs The Map that has the definitions of how many ships to output
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> fleetSpecs
) {
}
