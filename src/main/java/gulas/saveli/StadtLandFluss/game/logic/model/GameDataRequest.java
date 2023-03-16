package gulas.saveli.StadtLandFluss.game.logic.model;

import gulas.saveli.StadtLandFluss.game.logic.cat.Category;
import lombok.Data;

import java.util.List;

@Data
public class GameDataRequest {

    private List<String> usernames;
    private Boolean roundHasAdvanced;
    private List<String> categories           ;
    private Boolean setNewCharacters;


}
