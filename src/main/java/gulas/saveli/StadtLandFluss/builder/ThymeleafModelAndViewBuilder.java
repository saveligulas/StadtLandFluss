package gulas.saveli.StadtLandFluss.builder;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class ThymeleafModelAndViewBuilder {

    public ModelAndView build(String path) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(path);
        return modelAndView;
    }
}
