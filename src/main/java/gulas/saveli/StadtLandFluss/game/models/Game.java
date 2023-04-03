package gulas.saveli.StadtLandFluss.game.models;

import gulas.saveli.StadtLandFluss.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Boolean hasExpired = false;
    private Integer maxPlayers;

    public Game(String hostUsername, Integer rounds, List<Character> characters, List<Category> categories) {
        this.hostUsername = hostUsername;
        this.rounds = rounds;
        this.characters = characters;
        this.categories = categories;
    }

    public int getPlayerCount() {
        return this.players.size();
    }

    public boolean isFull() {
        return this.players.size() >= maxPlayers;
    }

    public boolean isPlayerInGame(String username) {
        List<String> usernames = new ArrayList<>();
        for(User player: this.players) {
            usernames.add(player.getEmail());
        }
        return usernames.contains(username);
    }

    public void addPlayer(User user) {
        this.players.add(user);
    }

    public void removePlayer(User user) {
        this.players.remove(user);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }
}
