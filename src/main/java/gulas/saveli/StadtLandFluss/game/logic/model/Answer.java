package gulas.saveli.StadtLandFluss.game.logic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
public class Answer {

    private Long id;
    private Long text;
    private Integer round;
}
