package gulas.saveli.StadtLandFluss.game.logic;

import gulas.saveli.StadtLandFluss.game.logic.model.GameData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class Game {

    @Autowired
    private final GameDataService gameDataService;
    @Autowired
    private final GameData gameData;


}
