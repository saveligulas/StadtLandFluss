package gulas.saveli.StadtLandFluss.game.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Category> categories = new ArrayList<>();
    @ElementCollection
    @OrderColumn
    private List<RoundAnswer> roundAnswers = new ArrayList<>();

    //TODO fix method
    //public void addAnswer(Answer answer) {
    //    this.list.add(answer);
    //}

    public void addCategory(Category category) {
        this.categories.add(category);
    }
}
