package dupradosantini.achievementsystem.controllers;

import com.google.common.net.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/")
public class LoginController {
    @PostMapping("login")
    public ResponseEntity<String> getLogin(){
        System.out.println("Login attempt");
        return ResponseEntity.ok().body("");
    }
}
