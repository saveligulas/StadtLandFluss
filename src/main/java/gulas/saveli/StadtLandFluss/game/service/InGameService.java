package gulas.saveli.StadtLandFluss.game.service;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.game.models.AnswerList;
import gulas.saveli.StadtLandFluss.game.models.Game;
import gulas.saveli.StadtLandFluss.game.models.resp.GameViewResponse;
import gulas.saveli.StadtLandFluss.repo.AnswerListRepository;
import gulas.saveli.StadtLandFluss.repo.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .currentRound(1) //TODO fix this set correct round
                .currentCharacter(game.getCurrentCharacter())
                .gameStarted(game.getHasStarted())
                .gameFinished(game.getHasExpired())
                .build();
    }

    public Boolean putAnswers(Long parseLong, String username, String answersStringList) {
        //TODO fill method
        String answerArray[] = answersStringList.split(",");
        for(int i = 0; i<answerArray.length; i++) {
            answerArray[i] = answerArray[i].trim();
        }

        Game game = gameRepository.findById(parseLong)
                .orElseThrow(() -> new ApiRequestException("game with id " + parseLong + " does not exist"));

        if(game.getUsernameAnswerMap().get(username) != null) {
            Long answerListId = game.getUsernameAnswerMap().get(username).getId();
            AnswerList answerList = answerListRepository.findById(answerListId)
                    .orElseThrow(() -> new ApiRequestException("answer_list with id " + answerListId + " does not exist"));
        }
        return true;
    }
}
