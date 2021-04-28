package bg.an.englishacademy.validation;

import bg.an.englishacademy.model.binding.UserEditBindingModel;
import bg.an.englishacademy.model.binding.UserRegisterBindingModel;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserValidationService {

    boolean userRegisterHasErrors(UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes);

    boolean userEditHasErrors(UserEditBindingModel userEditBindingModel, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, String currentUsername, String currentEmail);
}
