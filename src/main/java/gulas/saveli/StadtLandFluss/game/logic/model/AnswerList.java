package gulas.saveli.StadtLandFluss.game.logic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer_maplist")
public class AnswerList {
    @Id
    @GeneratedValue
    private Long id;
    @ElementCollection
    @OrderColumn
    private List<Category> categories;
    @ElementCollection
    @OrderColumn
    private List<Answer> list;
}
