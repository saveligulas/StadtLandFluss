package gulas.saveli.StadtLandFluss.game.controller;

import gulas.saveli.StadtLandFluss.builder.ThymeleafModelAndViewBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class AuthHomeController {

    private final ThymeleafModelAndViewBuilder thymeleafModelAndViewBuilder;

    @GetMapping
    public ModelAndView view() {
        return thymeleafModelAndViewBuilder.build("home");
    }

}
