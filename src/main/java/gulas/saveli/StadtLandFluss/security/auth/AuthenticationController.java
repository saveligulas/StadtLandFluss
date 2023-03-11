package gulas.saveli.StadtLandFluss.security.auth;

import gulas.saveli.StadtLandFluss.builder.ThymeleafModelAndViewBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService service;
    @Autowired
    private final ThymeleafModelAndViewBuilder thymeleafModelAndViewBuilder;

    @GetMapping("/register")
    public ModelAndView registerPage() {
        return thymeleafModelAndViewBuilder.build("register");
    }

    @GetMapping("/authenticate")
    public ModelAndView loginPage() {
        return thymeleafModelAndViewBuilder.build("authenticate");
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, value="/post/register")
    @ResponseBody
    public String register (
            @RequestBody RegisterRequest request
    ) {
        return service.register(request);
    }

    @CrossOrigin
    @PostMapping("/post/authenticate")
    @ResponseBody
    public AuthenticationResponse authenticate (
            @RequestBody AuthenticationRequest request
    ) {
        return (service.authenticate(request));
    }
}
