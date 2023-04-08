package gulas.saveli.StadtLandFluss.game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TimerTestController {

    @GetMapping("/test/timer")
    public ModelAndView timer() {
        return  new ModelAndView("timer");
    }
}
