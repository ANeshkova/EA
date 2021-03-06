package bg.an.englishacademy.web.controllers;

import bg.an.englishacademy.model.binding.WordAddBindingModel;
import bg.an.englishacademy.model.binding.WordEditBindingModel;
import bg.an.englishacademy.model.service.UserServiceModel;
import bg.an.englishacademy.model.service.WordServiceModel;
import bg.an.englishacademy.model.view.WordViewModel;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("/all/admin-table")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allWords(Model model) {

        List<WordViewModel> words = this.wordService
                .findAllWords()
                .stream()
                .map(w -> {
                    WordViewModel wordViewModel = this.modelMapper.map(w, WordViewModel.class);
                    wordViewModel.setCategory(w.getCategory().getName());
                    return wordViewModel;
                })
                .collect(Collectors.toList());

        model.addAttribute("words", words);

        return "words/words-all-admin-table";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editWord(@PathVariable Long id, Model model) {

        if (!model.containsAttribute("wordEditBindingModel")) {
            WordEditBindingModel wordEditBindingModel = new WordEditBindingModel();
            WordServiceModel wordServiceModel = this.wordService.findWordById(id);

            wordEditBindingModel.setEnglish(wordServiceModel.getEnglish());
            wordEditBindingModel.setBulgarian(wordServiceModel.getBulgarian());
            wordEditBindingModel.setCategory(wordServiceModel.getCategory().getName());

            model.addAttribute("wordEditBindingModel", wordEditBindingModel);
        }

        List<String> categories = this.categoryService.findAllCategoryNames();
        model.addAttribute("categories", categories);
        model.addAttribute("id", id);

        return "words/word-edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editWordConfirm(@Valid WordEditBindingModel wordEditBindingModel, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes, @PathVariable Long id) {

        if (this.wordValidationService.wordEditHasErrors(wordEditBindingModel, bindingResult, redirectAttributes, id)) {
            return super.redirect("/words/edit/" + id);
        }

        WordServiceModel wordServiceModel = this.modelMapper.map(wordEditBindingModel, WordServiceModel.class);
        wordServiceModel.setCategory(this.categoryService.findCategoryByName(wordEditBindingModel.getCategory()));

        this.wordService.editWord(id, wordServiceModel);
        redirectAttributes.addFlashAttribute("wordEditedSuccessfully", true);

        return super.redirect("/words/all/admin-table");
    }

    @GetMapping("/delete/{id}/{category}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteWord(@PathVariable Long id, @PathVariable String category, Model model) {
        model.addAttribute(id);
        model.addAttribute(category);
        return "words/word-delete";
    }

    @PostMapping("/delete/{id}/{category}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteWordConfirm(@PathVariable Long id, @PathVariable String category,
                                    RedirectAttributes redirectAttributes) {

        this.wordService.deleteWord(id);
        redirectAttributes.addFlashAttribute("wordDeletedSuccessfully", true);

        if (category.equals("all")) {
            return super.redirect("/words/all/admin-table");
        }

        return super.redirect("/words/all/admin-table/" + category);
    }

    @GetMapping("/all/{category}")
    @PreAuthorize("isAuthenticated()")
    public String details(Model model, @PathVariable String category, Principal principal) {

        List<WordViewModel> words =
                this.wordService.findAllWordsByCategory(category)
                        .stream()
                        .map(w -> {
                            WordViewModel wordViewModel = this.modelMapper.map(w, WordViewModel.class);
                            wordViewModel.setCategory(w.getCategory().getName());
                            return wordViewModel;
                        })
                        .collect(Collectors.toList());

        UserServiceModel userServiceModel = this.userService.findUserByUsername(principal.getName());

        Set<Long> userWords = userServiceModel.getWords()
                .stream().map(word -> word.getId()).collect(Collectors.toSet());

        model.addAttribute("userWords", userWords);
        model.addAttribute("words", words);
        model.addAttribute("category", category);

        return "words/words-all-by-category";
    }

    @PostMapping("/add-to-my/{id}")
    @PreAuthorize("isAuthenticated()")
    public String addToMy(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        WordServiceModel wordServiceModel = this.wordService.findWordById(id);
        UserServiceModel userServiceModel = this.userService.findUserByUsername(principal.getName());

        this.userService.addWordToUser(wordServiceModel, userServiceModel);
        String category = wordServiceModel.getCategory().getName();

        redirectAttributes.addFlashAttribute("wordAddedSuccessfully", true);
        redirectAttributes.addFlashAttribute("word", wordServiceModel.getEnglish());
        return redirect("/words/all/" + category);
    }

    @PostMapping("/remove-from-my/{endpoint}/{id}")
    @PreAuthorize("isAuthenticated()")
    public String removeFromMyRedirectCategory(@PathVariable String endpoint, @PathVariable Long id, Principal principal,
                                               RedirectAttributes redirectAttributes) {
        WordServiceModel wordServiceModel = this.wordService.findWordById(id);
        UserServiceModel userServiceModel = this.userService.findUserByUsername(principal.getName());

        this.userService.removeWordFromUser(wordServiceModel, userServiceModel);

        redirectAttributes.addFlashAttribute("wordRemovedSuccessfully", true);
        redirectAttributes.addFlashAttribute("word", wordServiceModel.getEnglish());

        if (endpoint.equals("my")) {
            return redirect("/words/my");
        } else {
            return redirect("/words/all/" + endpoint);
        }
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public String myWords (Model model, Principal principal) {
        UserServiceModel userServiceModel = this.userService.findUserByUsername(principal.getName());
        Set<WordServiceModel> myWords = userServiceModel.getWords();

        model.addAttribute("words", myWords);
        return "words/words-my";
    }

    @GetMapping("/all/admin-table/{category}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allWordsByCategory(Model model, @PathVariable String category) {

        List<WordViewModel> words =
                this.wordService.findAllWordsByCategory(category)
                        .stream()
                        .map(w -> {
                            WordViewModel wordViewModel = this.modelMapper.map(w, WordViewModel.class);
                            wordViewModel.setCategory(w.getCategory().getName());
                            return wordViewModel;
                        })
                        .collect(Collectors.toList());

        model.addAttribute("words", words);
        model.addAttribute("category", category);

        return "words/words-all-by-category-admin-table";
    }
}
