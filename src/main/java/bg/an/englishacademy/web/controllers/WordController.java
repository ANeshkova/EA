package bg.an.englishacademy.web.controllers;

import bg.an.englishacademy.model.binding.WordAddBindingModel;
import bg.an.englishacademy.model.service.WordServiceModel;
import bg.an.englishacademy.service.CategoryService;
import bg.an.englishacademy.service.UserService;
import bg.an.englishacademy.service.WordService;
import bg.an.englishacademy.validation.WordValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/words")
public class WordController extends BaseController {

    private final WordService wordService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final WordValidationService wordValidationService;

    public WordController(WordService wordService, CategoryService categoryService, UserService userService, ModelMapper modelMapper, WordValidationService wordValidationService) {
        this.wordService = wordService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.wordValidationService = wordValidationService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addWord(Model model) {

        if (!model.containsAttribute("wordAddBindingModel")) {
            model.addAttribute("wordAddBindingModel", new WordAddBindingModel());
        }
        model.addAttribute("categories", this.categoryService.findAllCategoryNames());

        return "words/word-add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addWordConfirm(@Valid WordAddBindingModel wordAddBindingModel, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (this.wordValidationService.wordAddHasErrors(wordAddBindingModel, bindingResult, redirectAttributes)) {
            return super.redirect("/words/add");
        }

        WordServiceModel wordServiceModel = this.modelMapper.map(wordAddBindingModel, WordServiceModel.class);
        wordServiceModel.setCategory(this.categoryService.findCategoryByName(wordAddBindingModel.getCategory()));

        this.wordService.addWord(wordServiceModel);
        redirectAttributes.addFlashAttribute("wordAddedSuccessfully", true);

        return super.redirect("/words/all/admin-table");
    }
}
