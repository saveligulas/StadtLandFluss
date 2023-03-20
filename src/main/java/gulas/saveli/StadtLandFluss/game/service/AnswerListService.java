package gulas.saveli.StadtLandFluss.game.service;

import gulas.saveli.StadtLandFluss.game.logic.model.AnswerList;
import gulas.saveli.StadtLandFluss.game.logic.model.Category;
import gulas.saveli.StadtLandFluss.repo.AnswerListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerListService {

    @Autowired
    private final AnswerListRepository answerListRepository;

    public List<AnswerList> setupEmptyAnswerLists(int amount, List<Category> categories) {
        List<AnswerList> answerLists = new ArrayList<>();
        for(int i = 0; i < amount; i++) {
            AnswerList answerList = new AnswerList(categories);
            answerListRepository.save(answerList);
            answerLists.add(answerList);
        }
        return answerLists;
    }
}
