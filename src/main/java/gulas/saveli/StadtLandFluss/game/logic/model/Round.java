package gulas.saveli.StadtLandFluss.game.logic.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Embeddable
public class Round {
    private Character character;
    private Map<String, List<Answer>> userAnswers;
    private Map<Answer, Boolean> answerVerified;
}
