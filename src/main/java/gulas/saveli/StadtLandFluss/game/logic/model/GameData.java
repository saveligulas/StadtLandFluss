package gulas.saveli.StadtLandFluss.game.logic.model;

import gulas.saveli.StadtLandFluss.game.logic.cat.Category;
import gulas.saveli.StadtLandFluss.game.logic.model.Round;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class GameData {

    private List<String> usernames;
    private Integer rounds;
    private Integer currentRound;
    private List<Category> categories;
    private List<Character> characters;
    private List<Round> roundsData;

}
