package gulas.saveli.StadtLandFluss.game.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameDataService {

    @Autowired
    private final GameData gameData;

}
