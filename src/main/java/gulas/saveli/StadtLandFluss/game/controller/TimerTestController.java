package gulas.saveli.StadtLandFluss.game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class TimerTestController {

    @GetMapping("/timer/{gameId}")
    public ModelAndView timer() {
        return  new ModelAndView("timer");
    }
}
