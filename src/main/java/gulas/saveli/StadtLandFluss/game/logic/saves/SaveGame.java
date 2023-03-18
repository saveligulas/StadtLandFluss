package gulas.saveli.StadtLandFluss.game.logic.saves;

import gulas.saveli.StadtLandFluss.game.logic.model.Answer;
import gulas.saveli.StadtLandFluss.game.logic.model.GameData;
import gulas.saveli.StadtLandFluss.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "save_games")
public class SaveGame {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "save_game_users",
            joinColumns = @JoinColumn(name = "save_game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

//    @ElementCollection
//    @CollectionTable(name = "savegame_user_list",
//            joinColumns = @JoinColumn(name = "savegame_id"))
//    @MapKeyJoinColumn(name = "user_id")
//    private Map<User, List<Answer>> userAnswerMap = new HashMap<>();
//
//    @ElementCollection
//    @CollectionTable(name = "savegame_user_score",
//            joinColumns = @JoinColumn(name = "savegame_id"))
//    @MapKeyJoinColumn(name = "user_id")
//    @Column(name = "user_integer")
//    private Map<User, Integer> userScoreMap = new HashMap<>();

//    @Embedded
//    private GameData gameData;
}
