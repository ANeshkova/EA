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

    @Override
    public boolean lessonEditHasErrors(LessonEditBindingModel lessonEditBindingModel, BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes, Long id) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("lessonEditBindingModel", lessonEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.lessonEditBindingModel", bindingResult);
            return true;
        }

        LessonServiceModel currentLesson = this.lessonService.findLessonById(id);

        if (this.lessonService.lessonTitleExists(lessonEditBindingModel.getTitle())
                && !lessonEditBindingModel.getTitle().equalsIgnoreCase(currentLesson.getTitle())) {
            redirectAttributes.addFlashAttribute("lessonEditBindingModel", lessonEditBindingModel);
            redirectAttributes.addFlashAttribute("lessonWithThisTitleAlreadyExists", true);
            return true;
        }

        return false;
    }
}
