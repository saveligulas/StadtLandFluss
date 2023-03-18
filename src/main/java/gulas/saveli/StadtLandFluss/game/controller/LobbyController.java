package gulas.saveli.StadtLandFluss.game.controller;

import gulas.saveli.StadtLandFluss.builder.ThymeleafModelAndViewBuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/lobby")
@RequiredArgsConstructor
public class LobbyController {

    private final ThymeleafModelAndViewBuilder thymeleafModelAndViewBuilder;

    @GetMapping
    public ModelAndView viewLobby() {
        return thymeleafModelAndViewBuilder.build("lobby");
    }

    @DeleteMapping("/{gameId}/disconnect/{username}")
    @ResponseBody
    @CrossOrigin
    public void disconnectUser(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
    }
}
