package bg.an.englishacademy.validation.impl;

import bg.an.englishacademy.model.binding.WordAddBindingModel;
import bg.an.englishacademy.model.binding.WordEditBindingModel;
import bg.an.englishacademy.model.service.WordServiceModel;
import bg.an.englishacademy.service.CategoryService;
import bg.an.englishacademy.service.WordService;
import bg.an.englishacademy.validation.WordValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class WordValidationServiceImpl implements WordValidationService {

    private final WordService wordService;
    private final CategoryService categoryService;

    public WordValidationServiceImpl(WordService wordService, CategoryService categoryService) {
        this.wordService = wordService;
        this.categoryService = categoryService;
    }

    @Override
    public boolean wordAddHasErrors(WordAddBindingModel wordAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("wordAddBindingModel", wordAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.wordAddBindingModel", bindingResult);
            return true;
        }

        if (this.wordService.englishWordExistsInCategory(wordAddBindingModel.getEnglish(), wordAddBindingModel.getCategory())) {
            redirectAttributes.addFlashAttribute("wordAddBindingModel", wordAddBindingModel);
            redirectAttributes.addFlashAttribute("englishWordAlreadyExistsInThisCategory", true);
            return true;
        }

        return false;
    }

    @Override
    public boolean wordEditHasErrors(WordEditBindingModel wordEditBindingModel, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, Long id) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("wordEditBindingModel", wordEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.wordEditBindingModel", bindingResult);
            return true;
        }

        WordServiceModel currentWord = this.wordService.findWordById(id);

        if (this.wordService.englishWordExistsInCategory(wordEditBindingModel.getEnglish(), wordEditBindingModel.getCategory())
                && !wordEditBindingModel.getEnglish().equalsIgnoreCase(currentWord.getEnglish())) {
            redirectAttributes.addFlashAttribute("wordEditBindingModel", wordEditBindingModel);
            redirectAttributes.addFlashAttribute("englishWordAlreadyExistsInThisCategory", true);
            return true;
        }

        return false;
    }
}
