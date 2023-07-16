package gulas.saveli.StadtLandFluss.game.models.checker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "city_checker")
public class City {
    @Id
    @GeneratedValue
    private Long id;
}
