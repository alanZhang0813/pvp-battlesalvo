package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Json Object that represents a Fleet, a collection of ships
 *
 * @param fleet A List of Ships, but ShipJson since we deal with Json objects
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipJson> fleet
) {
}
