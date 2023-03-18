package gulas.saveli.StadtLandFluss.game.logic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Boolean hasOfficialChecker;

    public Category(String name, Boolean hasOfficialChecker) {
        this.name = name;
        this.hasOfficialChecker = hasOfficialChecker;
    }
}
