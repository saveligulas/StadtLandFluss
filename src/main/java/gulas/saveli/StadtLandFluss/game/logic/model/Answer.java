package gulas.saveli.StadtLandFluss.game.logic.model;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class Answer {

    private Long text;
    private Integer round;

}
