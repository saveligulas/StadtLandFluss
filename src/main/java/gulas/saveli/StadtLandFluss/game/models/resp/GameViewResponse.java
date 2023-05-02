package gulas.saveli.StadtLandFluss.game.models.resp;

import gulas.saveli.StadtLandFluss.game.models.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameViewResponse {

    private Integer currentRound;
    private Integer rounds;
    private Character currentCharacter;
    private List<Category> categories;

}
