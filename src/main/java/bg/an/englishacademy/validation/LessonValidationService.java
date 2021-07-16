package bg.an.englishacademy.validation;

import bg.an.englishacademy.model.binding.LessonAddBindingModel;
import bg.an.englishacademy.model.binding.LessonEditBindingModel;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface LessonValidationService {

    boolean lessonAddHasErrors(LessonAddBindingModel lessonAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    boolean lessonEditHasErrors(LessonEditBindingModel lessonEditBindingModel, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Long id);
}
