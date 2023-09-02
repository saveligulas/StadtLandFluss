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
@Table(name= "dynamic_field", schema= "converter")
public class DynamicField {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dynamic_category_id")
    private DynamicCategory dynamicCategory;

    private String value;

}
