package bg.an.englishacademy.validation.impl;

import bg.an.englishacademy.model.binding.UserRegisterBindingModel;
import bg.an.englishacademy.service.UserService;
import bg.an.englishacademy.validation.UserValidationService;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
public class UserValidationServiceImpl implements UserValidationService {

    private final UserService userService;

    public UserValidationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean userRegisterHasErrors(UserRegisterBindingModel userRegisterBindingModel,
                                         BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return true;
        }

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("passwordsDontMatch", true);
            return true;
        }

        if (this.userService.usernameExists(userRegisterBindingModel.getUsername())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("usernameAlreadyExists", true);
            return true;
        }

        if (this.userService.emailExists(userRegisterBindingModel.getEmail())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("emailAlreadyExists", true);
            return true;
        }

        return false;
    }
}
