package gulas.saveli.StadtLandFluss.game.models.checker;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "distinct_name", schema = "converter")
public class DistinctName {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Long uses;

    @Enumerated(EnumType.STRING)
    private ModelType modelType;
}
