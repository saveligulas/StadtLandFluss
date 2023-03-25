package gulas.saveli.StadtLandFluss.game.logic.model.req;

import gulas.saveli.StadtLandFluss.game.logic.model.Category;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.OrderColumn;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameSettingRequest {

    private Integer rounds;
    private List<Character> characters = new ArrayList<>();
    private List<String> categoryNames = new ArrayList<>();

}
