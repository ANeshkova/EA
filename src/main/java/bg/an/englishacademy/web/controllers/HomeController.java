package bg.an.englishacademy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model, Principal principal) {

        if (principal == null) {
            return "index";
        } else {
            model.addAttribute("username", principal.getName());
            return "home";
        }
    }
}
