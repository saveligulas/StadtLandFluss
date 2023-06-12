package gulas.saveli.StadtLandFluss.game.controller;

import gulas.saveli.StadtLandFluss.builder.ThymeleafModelAndViewBuilder;

import gulas.saveli.StadtLandFluss.game.models.req.GameSettingRequest;
import gulas.saveli.StadtLandFluss.game.models.resp.GameInfoResponse;
import gulas.saveli.StadtLandFluss.game.models.resp.GameSettingResponse;
import gulas.saveli.StadtLandFluss.game.service.PreGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lobby")
@RequiredArgsConstructor
public class LobbyController {

    @Autowired
    private final ThymeleafModelAndViewBuilder thymeleafModelAndViewBuilder;
    @Autowired
    private final PreGameService preGameService;

    @GetMapping("/{gameId}")
    public ModelAndView viewLobby(@PathVariable("gameId") String gameId) {
        return thymeleafModelAndViewBuilder.build("lobby");
    }

    @PostMapping("/{gameId}/connect")
    @CrossOrigin
    public Boolean joinGame(@RequestHeader("Username") String username, @PathVariable("gameId") String gameId) {
        return preGameService.joinGame(Long.parseLong(gameId), username);
    }

    @DeleteMapping("/{gameId}/disconnect")
    @ResponseBody
    @CrossOrigin
    public void disconnectUser(@RequestHeader("Username") String username, @PathVariable("gameId") String gameId) {
        preGameService.disconnectUser(Long.parseLong(gameId), username);
    }

    @GetMapping("/{gameId}/info")
    @ResponseBody
    @CrossOrigin
    public GameInfoResponse viewGameInfo(@PathVariable("gameId") String gameId) {
        return preGameService.buildGameInfo(Long.parseLong(gameId));
    }

    @PostMapping("/{gameId}/host/settings")
    @ResponseBody
    @CrossOrigin
    public GameSettingResponse changeGameSettings(@PathVariable("gameId") String gameId,
                                                  @RequestParam(name = "categories", required = false) String categoryNames,
                                                  @RequestParam(name = "characters", required = false) String characterString,
                                                  @RequestParam(name = "rounds", required = false) String rounds) {
        List<String> categoriesNames = null;
        List<Character> characters = null;
        int numberOfRounds = 0;

        if (categoryNames != null && !categoryNames.isEmpty()) {
            categoriesNames = Arrays.asList(categoryNames.split(","));
        }
        if (characterString != null && !characterString.isEmpty()) {
            characters = Arrays.stream(characterString.split(",")).map(s -> s.charAt(0)).collect(Collectors.toList());
        }
        if (rounds != null && !rounds.isEmpty()) {
            numberOfRounds = Integer.parseInt(rounds);
        }

        GameSettingRequest gameSettingRequest = GameSettingRequest.builder()
                .categoryNames(categoriesNames)
                .characters(characters)
                .rounds(numberOfRounds)
                .build();
        return preGameService.changeGameSettings(Long.parseLong(gameId), gameSettingRequest);
    }

    @PostMapping("/{gameId}/host/remove/category")
    @ResponseBody
    @CrossOrigin
    public Boolean removeCategory(@PathVariable("gameId") String gameId, @RequestParam(name = "category_name", required = true) String categoryName) {
        return preGameService.removeCategory(Long.parseLong(gameId), categoryName);
    }

    @GetMapping("/{gameId}/host/check")
    @ResponseBody
    @CrossOrigin
    public Boolean isGameHost(@PathVariable("gameId") String gameId, @RequestHeader("HostUsername") String host) {
        return preGameService.isGameHost(Long.parseLong(gameId), host);
    }

    @GetMapping("/{gameId}/host/start")
    @CrossOrigin
    public Boolean startGame(@PathVariable("gameId") String gameId, @RequestHeader("HostUsername") String host) {
        return  preGameService.startGame(Long.parseLong(gameId), host);
    }
}
