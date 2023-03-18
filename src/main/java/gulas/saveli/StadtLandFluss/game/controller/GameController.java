package gulas.saveli.StadtLandFluss.game.controller;

import gulas.saveli.StadtLandFluss.builder.ThymeleafModelAndViewBuilder;
import gulas.saveli.StadtLandFluss.game.logic.GameDataService;
import gulas.saveli.StadtLandFluss.game.logic.cat.Category;
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

    @PostMapping ("/table/{gameId}/category")
    @ResponseBody
    @CrossOrigin
    public List<Category> setCategories(@PathVariable("gameId") Long gameId, List<String> categories) {
        return gameDataService.setCategories(gameId, categories);
    }

    @GetMapping("/table/{gameId}/category")
    @ResponseBody
    @CrossOrigin
    public List<String> getCategoriesOfCurrentGame(@PathVariable("gameId") Long gameId) {
        return gameDataService.getCategories(gameId);
    }

    @GetMapping("/table/{gameId}/character")
    @ResponseBody
    @CrossOrigin
    public Character getCurrentCharacter(@PathVariable("gameId") Long gameId){
        return gameDataService.getCurrentCharacter(gameId);
    }
}
