package gulas.saveli.StadtLandFluss.game.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Embeddable
@Data
public class RoundAnswer {

    private Integer round;
    private Character character;
    private Map<Category, String> categoryTextMap;

    public void addToCategoryTextMapping() {
    }
}
