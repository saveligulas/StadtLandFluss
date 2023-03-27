package gulas.saveli.StadtLandFluss.game.controller;

import gulas.saveli.StadtLandFluss.builder.ThymeleafModelAndViewBuilder;

import gulas.saveli.StadtLandFluss.game.logic.model.req.GameSettingRequest;
import gulas.saveli.StadtLandFluss.game.logic.model.resp.GameInfoResponse;
import gulas.saveli.StadtLandFluss.game.logic.model.resp.GameSettingResponse;
import gulas.saveli.StadtLandFluss.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/lobby")
@RequiredArgsConstructor
public class LobbyController {

    private final ThymeleafModelAndViewBuilder thymeleafModelAndViewBuilder;
    @Autowired
    private final GameService gameService;

    @GetMapping
    public ModelAndView viewLobby() {
        return thymeleafModelAndViewBuilder.build("lobby");
    }

    @PostMapping("/{gameId}/connect")
    @CrossOrigin
    public Boolean joinGame(@RequestHeader("Authorization") String token, @RequestHeader("Username") String username) {

    }

    @DeleteMapping("/{gameId}/disconnect")
    @ResponseBody
    @CrossOrigin
    public void disconnectUser(@RequestHeader("Authorization") String token, @RequestHeader("Username") String username) {
        //TODO
    }

    @GetMapping("/{gameId}")
    @ResponseBody
    @CrossOrigin
    public GameInfoResponse viewGameInfo(@PathVariable("gameId") String gameId) {
        return gameService.buildGameInfo(Long.parseLong(gameId));
    }

    @PostMapping("/{gameId}/host/settings")
    @ResponseBody
    @CrossOrigin
    public GameSettingResponse changeGameSettings(@PathVariable("gameId") String gameId, @RequestBody GameSettingRequest settingRequest) {
        return gameService.changeGameSettings(Long.parseLong(gameId), settingRequest);
    }

    @GetMapping("/{gameId}/host/check")
    @ResponseBody
    @CrossOrigin
    public Boolean isGameHost(@PathVariable("gameId") String gameId, @RequestHeader("HostUsername") String host) {
        return gameService.isGameHost(Long.parseLong(gameId), host);
    }
}
