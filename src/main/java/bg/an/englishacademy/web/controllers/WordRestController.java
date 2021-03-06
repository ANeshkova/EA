package bg.an.englishacademy.web.controllers;

import bg.an.englishacademy.model.service.UserServiceModel;
import bg.an.englishacademy.model.service.WordServiceModel;
import bg.an.englishacademy.model.view.WordViewModel;
import bg.an.englishacademy.service.UserService;
import bg.an.englishacademy.service.WordService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/words")
public class WordRestController {

    private final WordService wordService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public WordRestController(WordService wordService, UserService userService, ModelMapper modelMapper) {
        this.wordService = wordService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api")
    public ResponseEntity<List<WordViewModel>> findAll() {
        return ResponseEntity
                .ok()
                .body(this.wordService
                        .findAllWords()
                        .stream()
                        .map(w -> this.modelMapper.map(w, WordViewModel.class))
                        .collect(Collectors.toList()));
    }

    @GetMapping("/category/api/{category}")
    public ResponseEntity<List<WordViewModel>> findAllByCategory(@PathVariable String category) {
        return ResponseEntity
                .ok()
                .body(this.wordService
                        .findAllWordsByCategory(category)
                        .stream()
                        .map(w -> this.modelMapper.map(w, WordViewModel.class))
                        .collect(Collectors.toList()));
    }

    @GetMapping("/user/api")
    public ResponseEntity<Set<Long>> findAllByUser(Principal principal) {

        UserServiceModel userServiceModel = this.userService.findUserByUsername(principal.getName());
        Set<Long> userWords = userServiceModel.getWords()
                .stream().map(word -> word.getId()).collect(Collectors.toSet());

        return ResponseEntity
                .ok()
                .body(userWords);
    }

    @GetMapping("/add/{id}/api")
    public ResponseEntity<String> addToUser(Principal principal, @PathVariable Long id) {

        WordServiceModel wordServiceModel = this.wordService.findWordById(id);
        UserServiceModel userServiceModel = this.userService.findUserByUsername(principal.getName());

        this.userService.addWordToUser(wordServiceModel, userServiceModel);

        return ResponseEntity
                .ok()
                .body("added");
    }

    @GetMapping("/remove/{id}/api")
    public ResponseEntity<String> removeFromUser(Principal principal, @PathVariable Long id) {

        WordServiceModel wordServiceModel = this.wordService.findWordById(id);
        UserServiceModel userServiceModel = this.userService.findUserByUsername(principal.getName());

        this.userService.removeWordFromUser(wordServiceModel, userServiceModel);

        return ResponseEntity
                .ok()
                .body("removed");
    }
}
