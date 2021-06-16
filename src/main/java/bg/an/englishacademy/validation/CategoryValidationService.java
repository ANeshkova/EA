package bg.an.englishacademy.validation;

import bg.an.englishacademy.model.binding.CategoryAddBindingModel;
import bg.an.englishacademy.model.binding.CategoryEditBindingModel;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface CategoryValidationService {

    boolean categoryAddHasErrors(CategoryAddBindingModel categoryAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    boolean categoryEditHasErrors(CategoryEditBindingModel categoryEditBindingModel, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes, Long id);
}
