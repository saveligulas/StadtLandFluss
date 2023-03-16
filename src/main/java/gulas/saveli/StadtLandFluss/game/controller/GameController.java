package gulas.saveli.StadtLandFluss.game.controller;

import gulas.saveli.StadtLandFluss.builder.ThymeleafModelAndViewBuilder;
import gulas.saveli.StadtLandFluss.game.logic.GameDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    @Autowired
    private final ThymeleafModelAndViewBuilder thymeleafModelAndViewBuilder;
    @Autowired
    private final GameDataService gameDataService;

    @GetMapping
    public ModelAndView viewGame() {
        return thymeleafModelAndViewBuilder.build("game");
    }

    @GetMapping("/table/categories")
    @ResponseBody
    @CrossOrigin
    public List<String> getCategoriesOfCurrentGame() {
        return gameDataService.getCategories();
    }

    @GetMapping("/table/character")
    @ResponseBody
    @CrossOrigin
    public Character getCurrentCharacter() {
        return gameDataService.getCurrentCharacter();
    }
}
