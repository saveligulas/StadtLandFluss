package gulas.saveli.StadtLandFluss.game.logic;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.game.logic.cat.Category;
import gulas.saveli.StadtLandFluss.game.logic.model.GameData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameDataService {

    @Autowired
    private final GameData gameData;


    public void setCharactersAndRounds(List<Character> characters) {
        String characterString =  characters.stream().map(Object::toString).collect(Collectors.joining());
        if(!characterString.matches("[a-zA-Z]+")) {
            throw new ApiRequestException("Invalid characters contained in list");
        }
        String strLower = characterString.toLowerCase();
        boolean hasDuplicates = false;
        for (int i = 0; i < strLower.length(); i++) {
            for (int j = i + 1; j < strLower.length(); j++) {
                if (strLower.charAt(i) == strLower.charAt(j)) {
                    hasDuplicates = true;
                }
            }
        }
        if(hasDuplicates) {
            throw new ApiRequestException("Characters contains duplicates");
        }
        gameData.setRounds(characters.size());
        gameData.setCurrentRound(0);
        gameData.setCharacters(characters);
    }

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
