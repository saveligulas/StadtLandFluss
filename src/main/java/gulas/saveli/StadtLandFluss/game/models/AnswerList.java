package gulas.saveli.StadtLandFluss.game.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    public AnswerList(List<Category> categories) {
        this.categories = categories;
        this.list = new ArrayList<>();
    }

    public void addAnswer(Answer answer) {
        this.list.add(answer);
    }
}
