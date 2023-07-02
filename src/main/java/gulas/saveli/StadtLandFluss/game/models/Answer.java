package gulas.saveli.StadtLandFluss.game.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Embeddable
@Data
public class Answer {

    private Integer round;
    private Character character;
    private Map<Category, String> categoryTextMap;

    public void addToCategoryTextMap() {
    }
}
