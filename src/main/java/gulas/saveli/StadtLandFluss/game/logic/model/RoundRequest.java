package gulas.saveli.StadtLandFluss.game.logic.model;

import lombok.Data;

import java.util.List;

@Data
public class RoundRequest {

    private String username;
    private List<String> answers;
    private List<List<Boolean>> answersVerified;

}
