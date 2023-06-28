package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Json Object used to represent a list of Coordinate objects
 *
 * @param shots A List of CoordJson objects
 */
public record CoordinatesJson(
    @JsonProperty("coordinates") List<CoordJson> shots) {
}
