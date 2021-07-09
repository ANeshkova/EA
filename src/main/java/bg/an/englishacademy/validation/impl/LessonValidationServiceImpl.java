package bg.an.englishacademy.validation.impl;

import bg.an.englishacademy.model.binding.LessonAddBindingModel;
import bg.an.englishacademy.model.binding.LessonEditBindingModel;
import bg.an.englishacademy.model.service.LessonServiceModel;
import bg.an.englishacademy.service.LessonService;
import bg.an.englishacademy.validation.LessonValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class LessonValidationServiceImpl implements LessonValidationService {

    private final LessonService lessonService;

    public LessonValidationServiceImpl(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public boolean lessonAddHasErrors(LessonAddBindingModel lessonAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("lessonAddBindingModel", lessonAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.lessonAddBindingModel", bindingResult);
            return true;
        }

        if (this.lessonService.lessonTitleExists(lessonAddBindingModel.getTitle())) {
            redirectAttributes.addFlashAttribute("lessonAddBindingModel", lessonAddBindingModel);
            redirectAttributes.addFlashAttribute("lessonWithThisTitleAlreadyExists", true);
            return true;
        }

        return false;
    }
}
