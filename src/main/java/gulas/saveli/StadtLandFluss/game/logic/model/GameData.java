package gulas.saveli.StadtLandFluss.game.logic.model;

import gulas.saveli.StadtLandFluss.game.logic.cat.Category;
import gulas.saveli.StadtLandFluss.game.logic.model.Round;
import gulas.saveli.StadtLandFluss.user.User;
import jakarta.persistence.*;
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
    @ManyToOne
    private List<User> users;
    private Integer rounds;
    private Integer currentRound;
    private List<Category> categories;
    private List<Character> characters;
    @Embedded
    private List<Round> roundsData;

    public GameData(List<User> users, Integer rounds, Integer currentRound, List<Category> categories, List<Character> characters, List<Round> roundsData) {
        this.users = users;
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
