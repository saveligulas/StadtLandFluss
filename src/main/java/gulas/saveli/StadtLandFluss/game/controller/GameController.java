package gulas.saveli.StadtLandFluss.game.controller;

import gulas.saveli.StadtLandFluss.game.models.resp.GameViewResponse;
import gulas.saveli.StadtLandFluss.game.service.InGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    @Autowired
    private final InGameService inGameService;

    @GetMapping("/{gameId}")
    public ModelAndView getGameById(@PathVariable("gameId") String gameId) {
        GameViewResponse response = inGameService.getGameViewResponse(Long.parseLong(gameId));
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("roundNumber", response.getRounds());
        objectMap.put("currentRound", response.getCurrentRound());
        objectMap.put("characterName", response.getCurrentCharacter());
        objectMap.put("categories", response.getCategories());
        ModelAndView result = new ModelAndView();
        result.setViewName("game");
        result.addAllObjects(objectMap);
        return result;
    }

    @GetMapping("/{gameId}/started")
    public Boolean checkIfGameHasStarted(@PathVariable("gameId") String gameId) {
        return inGameService.getGameViewResponse(Long.parseLong(gameId)).getGameFinished();
    }

    @GetMapping("/{gameId}/expired")
    public Boolean checkIfGameHasExpired(@PathVariable("gameId") String gameId) {
        return inGameService.getGameViewResponse(Long.parseLong(gameId)).getGameFinished();
    }

    @PostMapping("/{gameId}/submit")
    public Boolean submitAnswers(@PathVariable("gameId") String gameId, @RequestParam("username") String username, @RequestParam("answers") String answersStringList) {
        return inGameService.putRoundAnswers(Long.parseLong(gameId), username, answersStringList);
    }

}
