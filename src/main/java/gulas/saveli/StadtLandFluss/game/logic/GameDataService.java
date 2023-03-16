package gulas.saveli.StadtLandFluss.game.logic;

import gulas.saveli.StadtLandFluss.game.logic.cat.Category;
import gulas.saveli.StadtLandFluss.game.logic.model.GameData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameDataService {

    @Autowired
    private final GameData gameData;


    public Character getCurrentCharacter() {
        int currentRound = gameData.getCurrentRound();
        return gameData.getCharacters().get(currentRound);
    }

    public List<String> getCategories() {
        List<String> categoryNames = new ArrayList<>();
        for(Category category : gameData.getCategories()) {
            categoryNames.add(category.getName());
        }
        return categoryNames;
    }
}
