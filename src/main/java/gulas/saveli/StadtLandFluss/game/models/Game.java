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
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    private List<User> players = new ArrayList<>();
    private String hostUsername;
    private Integer rounds;
    @OrderColumn
    private List<Character> characters = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "game_categories_updated",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "username")
    private Map<String, AnswerList> usernameAnswerMap = new HashMap<>();

    private Boolean hasStarted = false;
    private Boolean hasExpired = false;
    private Integer maxPlayers;
    private Integer currentRound = 0;

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
        if (this.players.size() == 0 || this.maxPlayers == 0 || this.maxPlayers == null || this.players == null) {
            return false;
        }
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

    public void advanceRound() {
        this.currentRound++;
    }

    public Character getCurrentCharacter() {
        return Character.toUpperCase(this.characters.get(this.currentRound-1));
    }
}
