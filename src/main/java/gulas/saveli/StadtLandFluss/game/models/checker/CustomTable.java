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
@Table(name= "custom_table", schema= "converter", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class CustomTable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String extension;

    @OneToMany(mappedBy = "customTable", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderColumn
    @ToString.Exclude
    private List<DynamicCategory> dynamicCategories = new ArrayList<>();

}
