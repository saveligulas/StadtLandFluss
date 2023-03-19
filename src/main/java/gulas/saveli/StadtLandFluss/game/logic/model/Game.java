package gulas.saveli.StadtLandFluss.game.logic.model;

import gulas.saveli.StadtLandFluss.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(targetEntity = User.class)
    private List<User> players;
    private String hostUsername;
    private Integer rounds;
    @OrderColumn
    private List<Character> characters = new ArrayList<>();
    @ElementCollection
    @OrderColumn
    private List<Category> categories = new ArrayList<>();
    @ElementCollection
    private Map<String, AnswerList> usernameAnswerMap = new HashMap<>();
    private Boolean hasStarted = false;

    public Game(String hostUsername, Integer rounds, List<Character> characters, List<Category> categories) {
        this.hostUsername = hostUsername;
        this.rounds = rounds;
        this.characters = characters;
        this.categories = categories;
    }

    public int getPlayerCount() {
        return this.players.size();
    }
}
