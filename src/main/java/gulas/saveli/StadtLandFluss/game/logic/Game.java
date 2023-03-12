package gulas.saveli.StadtLandFluss.game.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class Game {

    private final GameDataService gameDataService;
    @Autowired
    private final GameData gameData;


}
