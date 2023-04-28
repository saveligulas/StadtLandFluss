package gulas.saveli.StadtLandFluss.game.controller;

import gulas.saveli.StadtLandFluss.builder.ThymeleafModelAndViewBuilder;
import gulas.saveli.StadtLandFluss.game.models.resp.GameViewResponse;
import gulas.saveli.StadtLandFluss.game.service.GameService;
import gulas.saveli.StadtLandFluss.game.service.InGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    @Autowired
    private final InGameService inGameService;

    @GetMapping("/{gameId}")
    public ModelAndView getGameById(@PathVariable("gameId") String gameId) {
        GameViewResponse response = inGameService.getGameViewResponse(Long.parseLong(gameId));
        Map<String, Object> objectMap = new HashMap<>();

        return new ModelAndView("game").addAllObjects(objectMap);
    }

}
