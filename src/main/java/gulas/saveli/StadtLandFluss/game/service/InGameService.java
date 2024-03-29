package gulas.saveli.StadtLandFluss.game.service;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.game.models.AnswerList;
import gulas.saveli.StadtLandFluss.game.models.Category;
import gulas.saveli.StadtLandFluss.game.models.Game;
import gulas.saveli.StadtLandFluss.game.models.RoundAnswer;
import gulas.saveli.StadtLandFluss.game.models.resp.GameViewResponse;
import gulas.saveli.StadtLandFluss.repo.AnswerListRepository;
import gulas.saveli.StadtLandFluss.repo.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InGameService {

    @Autowired
    private final GameRepository gameRepository;
    @Autowired
    private final AnswerListRepository answerListRepository;

    public GameViewResponse getGameViewResponse(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ApiRequestException("game with id " + gameId + " does not exist"));
        return GameViewResponse.builder()
                .categories(game.getCategories())
                .rounds(game.getRounds())
                .currentRound(game.getCurrentRound())
                .currentCharacter(game.getCurrentCharacter()) //fix index out of bounds
                .gameStarted(game.getHasStarted())
                .gameFinished(game.getHasExpired())
                .build();
    }

    @Transactional
    public Boolean putRoundAnswers(Long parseLong, String username, String answersStringList) {
        String[] answerArray = answersStringList.split(",");
        for(int i = 0; i<answerArray.length; i++) {
            answerArray[i] = answerArray[i].trim();
        }

        Game game = gameRepository.findById(parseLong)
                .orElseThrow(() -> new ApiRequestException("game with id " + parseLong + " does not exist"));

        if(game.getUsernameAnswerMap().get(username) != null) {
            Long answerListId = game.getUsernameAnswerMap().get(username).getId();
            AnswerList answerList = answerListRepository.findById(answerListId)
                    .orElseThrow(() -> new ApiRequestException("answer_list with id " + answerListId + " does not exist"));

            answerList.setCategories(game.getCategories());
            RoundAnswer roundAnswer = new RoundAnswer();
            roundAnswer.setAnswers(List.of(answerArray));
            roundAnswer.setRound(game.getCurrentRound());
            roundAnswer.setCharacter(game.getCurrentCharacter());
            answerList.addRoundAnswer(roundAnswer);
        } else {
            AnswerList answerListProxy = new AnswerList();
            answerListProxy.setUsername(username);
            answerListRepository.save(answerListProxy);

            AnswerList answerList = answerListRepository.findByName(username)
                            .orElseThrow(() -> new ApiRequestException("Couldn't find answerList for username " + username));

            answerList.setCategories(game.getCategories());
            answerList.setUsername(username);
            RoundAnswer roundAnswer = new RoundAnswer();
            roundAnswer.setAnswers(List.of(answerArray));
            roundAnswer.setRound(game.getCurrentRound());
            roundAnswer.setCharacter(game.getCurrentCharacter());
            answerList.addRoundAnswer(roundAnswer);

            game.getUsernameAnswerMap().put(username, answerList);
        }

        return true;
    }

    protected Game updateGameRound(Game game) {
        game.setCurrentRound(game.getCurrentRound()+1);
        return game;
    }

    private Map<Category, Boolean> checkAnswerWithOfficialChecker(List<Category> categories) {
        for(Category category : categories) {
            if(category.getHasOfficialChecker()) {
                //TODO add Database with for official checker
            }
        }
        return new HashMap<>();
    }
}
