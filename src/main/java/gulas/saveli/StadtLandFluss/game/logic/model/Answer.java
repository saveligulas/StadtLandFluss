package gulas.saveli.StadtLandFluss.game.logic.model;

import gulas.saveli.StadtLandFluss.game.logic.cat.Category;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Answer {
    private Category category;
    private String answer;
}
