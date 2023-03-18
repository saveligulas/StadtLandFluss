package gulas.saveli.StadtLandFluss.game.controller;

import gulas.saveli.StadtLandFluss.builder.ThymeleafModelAndViewBuilder;
import gulas.saveli.StadtLandFluss.game.logic.GameDataService;
import gulas.saveli.StadtLandFluss.game.logic.model.response.GameDataResponse;
import gulas.saveli.StadtLandFluss.security.logger.UserLoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/lobby")
@RequiredArgsConstructor
public class LobbyController {

    private final ThymeleafModelAndViewBuilder thymeleafModelAndViewBuilder;
    private final GameDataService gameDataService;

    @GetMapping
    public ModelAndView viewLobby() {
        return thymeleafModelAndViewBuilder.build("lobby");
    }

    @GetMapping("/games")
    @ResponseBody
    @CrossOrigin
    public List<GameDataResponse> getGames() {
        return gameDataService.getHostedGames();
    }

    @GetMapping("/{gameId}/users")
    @ResponseBody
    @CrossOrigin
    public List<String> getConnectedUsers(@PathVariable("gameId") Long gameId) {
        return gameDataService.getConnectedUsernames(gameId);
    }

    @DeleteMapping("/{gameId}/disconnect/{username")
    @ResponseBody
    @CrossOrigin
    public void disconnectUser(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
    }
}
