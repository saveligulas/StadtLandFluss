package gulas.saveli.StadtLandFluss.game.models.checker;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "dynamic_category", schema = "converter")
public class DynamicCategory {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "customTable_id")
    private CustomTable customTable;

    @OneToMany(mappedBy = "dynamicCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn
    @ToString.Exclude
    private List<DynamicField> dynamicFields = new ArrayList<>();

}
