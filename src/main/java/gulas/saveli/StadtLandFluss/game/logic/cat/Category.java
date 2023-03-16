package gulas.saveli.StadtLandFluss.game.logic.cat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Boolean hasOfficialChecker;

    public Category() {

    }

    public Category(String name, Boolean hasOfficialChecker) {
        this.name = name;
        this.hasOfficialChecker = hasOfficialChecker;
    }

    public Category(Long id, String name, Boolean hasOfficialChecker) {
        this.id = id;
        this.name = name;
        this.hasOfficialChecker = hasOfficialChecker;
    }
}
