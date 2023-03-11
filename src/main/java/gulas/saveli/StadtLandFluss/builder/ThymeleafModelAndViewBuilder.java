package gulas.saveli.StadtLandFluss.builder;

import org.springframework.web.servlet.ModelAndView;

public class ThymeleafModelAndViewBuilder {

    public ModelAndView build(String path) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(path);
        return modelAndView;
    }
}
