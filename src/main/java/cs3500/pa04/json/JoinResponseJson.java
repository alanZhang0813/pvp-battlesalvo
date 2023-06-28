package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.view.GameType;

/**
 * Json Object that represents the return format for the Join server request
 *
 * @param name The GitHub username of my AI
 * @param gameType The GameType enum, either SINGLE or MULTI, SINGLE for this assignment
 */
public record JoinResponseJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType gameType
) {
}
