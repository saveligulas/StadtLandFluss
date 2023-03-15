package gulas.saveli.StadtLandFluss.game.logic.saves;

import gulas.saveli.StadtLandFluss.game.logic.model.Answer;
import gulas.saveli.StadtLandFluss.game.logic.model.GameData;
import gulas.saveli.StadtLandFluss.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SaveGame {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "user_save_games",
            joinColumns = @JoinColumn(name = "save_game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;
    @ElementCollection
    @CollectionTable(name = "user_answers", joinColumns = @JoinColumn(name = "save_game_id"))
    @MapKeyJoinColumn(name = "user_id")
    private Map<User, List<Answer>> userAnswers;
    @ElementCollection
    @CollectionTable(name = "save_game_scores", joinColumns = @JoinColumn(name = "save_game_id"))
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "value")
    private Map<User, Integer> scores;
    @Embedded
    private GameData gameData;
}
