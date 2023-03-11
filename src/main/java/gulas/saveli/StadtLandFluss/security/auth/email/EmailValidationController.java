package gulas.saveli.StadtLandFluss.security.auth.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/validator")
@RequiredArgsConstructor
public class EmailValidationController {

    @Autowired
    private final EmailValidationService emailValidationService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, value="/email")
    @ResponseBody
    public String register (@RequestBody EmailRequest request) {
        return emailValidationService.validateEmail(request.getEmail());
    }
}
