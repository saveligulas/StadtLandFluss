package gulas.saveli.StadtLandFluss.game.logic;

import gulas.saveli.StadtLandFluss.game.logic.cat.Category;
import gulas.saveli.StadtLandFluss.user.User;
import lombok.Data;

import java.util.List;

@Data
public class GameData {

    private List<User> players;
    private Integer rounds;
    private Integer currentRound;
    private List<Category> categories;
    private List<Character> characters;

}
