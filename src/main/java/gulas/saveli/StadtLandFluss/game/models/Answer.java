package gulas.saveli.StadtLandFluss.game.models;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class Answer {

    private Long text;
    private Integer round;
    private Character character;

}
