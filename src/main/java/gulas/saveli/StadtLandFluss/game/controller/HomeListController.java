package gulas.saveli.StadtLandFluss.game.controller;

import gulas.saveli.StadtLandFluss.builder.ThymeleafModelAndViewBuilder;
import gulas.saveli.StadtLandFluss.game.models.resp.GameListResponse;
import gulas.saveli.StadtLandFluss.game.service.PreGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeListController {

    private final ThymeleafModelAndViewBuilder thymeleafModelAndViewBuilder;
    @Autowired
    private final PreGameService preGameService;

    @GetMapping
    public ModelAndView view() {
        return thymeleafModelAndViewBuilder.build("home");
    }

    @GetMapping("/list")
    @ResponseBody
    @CrossOrigin
    public List<GameListResponse> getGamesResponseList() {
        return preGameService.getGamesResponseList();
    }

    @PostMapping("/host")
    @ResponseBody
    @CrossOrigin
    public Long hostGame(@RequestHeader("HostUsername") String host) {
        return preGameService.hostGame(host);
    }

}
