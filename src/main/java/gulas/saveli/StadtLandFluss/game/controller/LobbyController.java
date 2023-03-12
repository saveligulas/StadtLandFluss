package gulas.saveli.StadtLandFluss.game.controller;

import gulas.saveli.StadtLandFluss.builder.ThymeleafModelAndViewBuilder;
import gulas.saveli.StadtLandFluss.security.logger.UserLoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/game/lobby")
@RequiredArgsConstructor
public class LobbyController {

    private final ThymeleafModelAndViewBuilder thymeleafModelAndViewBuilder;
    private final UserLoggerService loggerService;

    @GetMapping
    public ModelAndView viewLobby() {
        return thymeleafModelAndViewBuilder.build("lobby");
    }

    @GetMapping("/auth/users")
    @ResponseBody
    @CrossOrigin
    public List<String> getConnectedUsers() {
        System.out.println(loggerService.getConnectedUsersNames());
        return loggerService.getConnectedUsersNames();
    }

    @DeleteMapping("/auth/users/{username}")
    @ResponseBody
    public void removeConnectedUser(@PathVariable String username) {
        loggerService.delete(username);
    }


}
