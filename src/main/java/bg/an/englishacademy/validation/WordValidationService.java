package bg.an.englishacademy.validation;

import bg.an.englishacademy.model.binding.WordAddBindingModel;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface WordValidationService {

    boolean wordAddHasErrors(WordAddBindingModel wordAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes);

}
