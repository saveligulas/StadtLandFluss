package gulas.saveli.StadtLandFluss.game.logic.model;

import gulas.saveli.StadtLandFluss.game.logic.cat.Category;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Embeddable
public class Answer {

    private String categoryName;
    private String answer;

}
