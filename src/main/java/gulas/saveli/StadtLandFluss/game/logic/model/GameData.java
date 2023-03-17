package gulas.saveli.StadtLandFluss.game.logic.model;

import gulas.saveli.StadtLandFluss.game.logic.cat.Category;
import gulas.saveli.StadtLandFluss.game.logic.model.Round;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameData {

    @Id
    @GeneratedValue
    private Long id;
    private List<String> usernames;
    private Integer rounds;
    private Integer currentRound;
    private List<Category> categories;
    private List<Character> characters;
    private List<Round> roundsData;

    public GameData(List<String> usernames, Integer rounds, Integer currentRound, List<Category> categories, List<Character> characters, List<Round> roundsData) {
        this.usernames = usernames;
        this.rounds = rounds;
        this.currentRound = currentRound;
        this.categories = categories;
        this.characters = characters;
        this.roundsData = roundsData;
    }

    public void advanceRound() {
        this.currentRound++;
    }

    public void addRoundData(Round round) {
        this.roundsData.add(round);
    }

}
