package dupradosantini.achievementsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@Controller
@RequestMapping(value = "/")
public class LoginController {
    @PostMapping("login")
    public String getLogin(){
        return "login";
    }
}
