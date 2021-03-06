package bg.an.englishacademy.web.controllers;

import bg.an.englishacademy.model.binding.UserEditBindingModel;
import bg.an.englishacademy.model.binding.UserRegisterBindingModel;
import bg.an.englishacademy.model.service.RoleServiceModel;
import bg.an.englishacademy.model.service.UserServiceModel;
import bg.an.englishacademy.model.view.UserProfileViewModel;
import bg.an.englishacademy.model.view.UserViewModel;
import bg.an.englishacademy.service.UserService;
import bg.an.englishacademy.validation.UserValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/login-error")
    public String failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                      String username, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("badCredentials", true);
        redirectAttributes.addFlashAttribute("username", username);

        return super.redirect("/users/login");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Principal principal, Model model) {

        UserProfileViewModel userProfileViewModel = this.modelMapper
                .map(this.userService.findUserByUsername(principal.getName()), UserProfileViewModel.class);

        model.addAttribute("userProfileViewModel", userProfileViewModel);

        return "users/profile";
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public String editProfile(Principal principal, Model model) {

        if (!model.containsAttribute("userEditBindingModel")) {
            UserEditBindingModel userEditBindingModel = new UserEditBindingModel();
            UserServiceModel userServiceModel = this.userService.findUserByUsername(principal.getName());

            userEditBindingModel.setUsername(userServiceModel.getUsername());
            userEditBindingModel.setEmail(userServiceModel.getEmail());

            model.addAttribute("userEditBindingModel", userEditBindingModel);
        }

        return "users/profile-edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public String editProfileConfirm(@Valid UserEditBindingModel userEditBindingModel,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

        UserServiceModel principalUserServiceModel = this.userService.findUserByUsername(principal.getName());

        if (this.userValidationService.userEditHasErrors(userEditBindingModel, bindingResult, redirectAttributes,
                principalUserServiceModel.getUsername(), principalUserServiceModel.getEmail())) {
            return super.redirect("/users/edit");
        }

        try {
            this.userService.editUserProfile(this.modelMapper.map(userEditBindingModel, UserServiceModel.class),
                    userEditBindingModel.getOldPassword(), principalUserServiceModel.getId());
            redirectAttributes.addFlashAttribute("profileEditedSuccessfully", true);
            return super.redirect("/users/profile");

        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute("exception", true);
            redirectAttributes.addFlashAttribute("exceptionMessage", exception.getMessage());
            return super.redirect("/users/edit");
        }
    }

    @GetMapping("/all/admin-table")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allUsers(Model model) {
        List<UserViewModel> users = this.userService
                .findAllUsers()
                .stream()
                .map(u -> {
                    UserViewModel userViewModel = this.modelMapper.map(u, UserViewModel.class);
                    userViewModel.setRoles(
                            u.getRoles()
                                    .stream()
                                    .map(RoleServiceModel::getRole)
                                    .collect(Collectors.toSet()));

                    return userViewModel;
                })
                .collect(Collectors.toList());

        model.addAttribute("users", users);

        return "users/users-all-admin-table";
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String setUser(@PathVariable Long id) {
        this.userService.setUserRole(id, "user");

        return super.redirect("/users/all/admin-table");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String setAdmin(@PathVariable Long id) {
        this.userService.setUserRole(id, "admin");

        return super.redirect("/users/all/admin-table");
    }
}
