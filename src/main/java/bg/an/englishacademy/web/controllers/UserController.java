package bg.an.englishacademy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/register")
    public String register() {
        return "users/register";
    }

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }
}
