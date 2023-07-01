package gulas.saveli.StadtLandFluss.game.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
public class Answer {

    private Long text;
    private Integer round;
    private Character character;

}
