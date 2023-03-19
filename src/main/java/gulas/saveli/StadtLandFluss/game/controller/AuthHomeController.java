package gulas.saveli.StadtLandFluss.game.controller;

import gulas.saveli.StadtLandFluss.builder.ThymeleafModelAndViewBuilder;
import gulas.saveli.StadtLandFluss.game.logic.model.Game;
import gulas.saveli.StadtLandFluss.game.logic.model.resp.GameResponse;
import gulas.saveli.StadtLandFluss.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class AuthHomeController {

    private final ThymeleafModelAndViewBuilder thymeleafModelAndViewBuilder;
    @Autowired
    private final GameService gameService;

    @GetMapping
    public ModelAndView view() {
        return thymeleafModelAndViewBuilder.build("home");
    }

    @GetMapping("/list")
    @ResponseBody
    @CrossOrigin
    public List<GameResponse> getGamesResponseList() {
        return gameService.getGamesResponseList();
    }

    @PostMapping("/host")
    @ResponseBody
    @CrossOrigin
    public Game hostGame(@RequestHeader("HostUsername") String host) {
        return gameService.hostGame(host);
    }

}
