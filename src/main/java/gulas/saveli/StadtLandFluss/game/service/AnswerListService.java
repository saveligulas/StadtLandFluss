package gulas.saveli.StadtLandFluss.game.service;

import gulas.saveli.StadtLandFluss.repo.AnswerListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerListService {

    @Autowired
    private final AnswerListRepository answerListRepository;


}
