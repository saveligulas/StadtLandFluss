package gulas.saveli.StadtLandFluss.game.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Embeddable
@Data
public class RoundAnswer {

    private Integer round;
    private Character character;
    private List<String> answers;

    public void addAnswer(String answer) {
        this.answers.add(answer);
    }
}
