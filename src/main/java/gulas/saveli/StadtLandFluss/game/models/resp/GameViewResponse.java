package gulas.saveli.StadtLandFluss.game.models.resp;

import lombok.Data;

import java.util.List;

@Data
public class GameViewResponse {

    private Integer rounds;
    private Character currentCharacter;
    private List<CategoryResponse> categories;

}
