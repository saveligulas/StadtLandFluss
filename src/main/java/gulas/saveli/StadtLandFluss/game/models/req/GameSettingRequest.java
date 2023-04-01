package gulas.saveli.StadtLandFluss.game.models.req;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GameSettingRequest {

    private Integer rounds;
    private List<Character> characters = new ArrayList<>();
    private List<String> categoryNames = new ArrayList<>();

}
