package gulas.saveli.StadtLandFluss.game.logic;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.game.logic.cat.Category;
import gulas.saveli.StadtLandFluss.game.logic.model.GameData;
import gulas.saveli.StadtLandFluss.repo.CategoryRepository;
import gulas.saveli.StadtLandFluss.repo.GameDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GameDataService {

    @Autowired
    private final GameDataRepository gameDataRepository;

    public void setCharactersAndRounds(Long gameDataId, List<Character> characters) {
        GameData gameData = gameDataRepository.findById(gameDataId)
                .orElseThrow(() -> new ApiRequestException("could not find game with id " + gameDataId));
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

    public Character getCurrentCharacter(Long gameDataId) {
        GameData gameData = gameDataRepository.findById(gameDataId)
                .orElseThrow(() -> new ApiRequestException("could not find game with id " + gameDataId));
        int currentRound = gameData.getCurrentRound();
        return gameData.getCharacters().get(currentRound);
    }

    public List<Character> getAllCharacters(Long gameDataId) {
        GameData gameData = gameDataRepository.findById(gameDataId)
                .orElseThrow(() -> new ApiRequestException("could not find game with id " + gameDataId));
        return gameData.getCharacters();
    }

    public List<String> getCategories(Long gameDataId) {
        GameData gameData = gameDataRepository.findById(gameDataId)
                .orElseThrow(() -> new ApiRequestException("could not find game with id " + gameDataId));
        List<String> categoryNames = new ArrayList<>();
        for(Category category : gameData.getCategories()) {
            categoryNames.add(category.getName());
        }
        return categoryNames;
    }
}
