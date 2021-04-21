package bg.an.englishacademy.web.controllers;

import bg.an.englishacademy.model.binding.UserRegisterBindingModel;
import bg.an.englishacademy.model.service.UserServiceModel;
import bg.an.englishacademy.service.UserService;
import bg.an.englishacademy.validation.UserValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserValidationService userValidationService;

    public UserController(UserService userService, ModelMapper modelMapper,
                          UserValidationService userValidationService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userValidationService = userValidationService;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String register(Model model) {

        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }

        return "users/register";
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (this.userValidationService.userRegisterHasErrors(userRegisterBindingModel, bindingResult, redirectAttributes)) {
            return super.redirect("/users/register");
        }

        this.userService.registerUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        redirectAttributes.addFlashAttribute("registrationSuccessful", true);
        return super.redirect("/users/login");
    }

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }
}
