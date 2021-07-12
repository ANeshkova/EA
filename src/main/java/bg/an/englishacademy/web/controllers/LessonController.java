package bg.an.englishacademy.web.controllers;

import bg.an.englishacademy.model.binding.LessonAddBindingModel;
import bg.an.englishacademy.model.binding.LessonEditBindingModel;
import bg.an.englishacademy.model.service.LessonServiceModel;
import bg.an.englishacademy.model.view.LessonViewModel;
import bg.an.englishacademy.service.LessonService;
import bg.an.englishacademy.validation.LessonValidationService;
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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lessons")
public class LessonController extends BaseController{

    private final LessonService lessonService;
    private final ModelMapper modelMapper;
    private final LessonValidationService lessonValidationService;

    public LessonController(LessonService lessonService, ModelMapper modelMapper, LessonValidationService lessonValidationService) {
        this.lessonService = lessonService;
        this.modelMapper = modelMapper;
        this.lessonValidationService = lessonValidationService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addLesson(Model model) {

        if (!model.containsAttribute("lessonAddBindingModel")) {
            model.addAttribute("lessonAddBindingModel", new LessonAddBindingModel());
        }

        return "lessons/lesson-add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addLessonConfirm(@Valid LessonAddBindingModel lessonAddBindingModel, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if(this.lessonValidationService.lessonAddHasErrors(lessonAddBindingModel, bindingResult, redirectAttributes)){
            return super.redirect("/lessons/add");
        }

        this.lessonService.addLesson(this.modelMapper.map(lessonAddBindingModel, LessonServiceModel.class));
        redirectAttributes.addFlashAttribute("lessonAddedSuccessfully", true);

        return super.redirect("/lessons/all/admin-table");
    }

    @GetMapping("/all/admin-table")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allLessonsAdminTable(Model model) {

        List<LessonViewModel> lessons = this.lessonService
                .findAllLessons()
                .stream()
                .map(l -> this.modelMapper.map(l, LessonViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("lessons", lessons);

        return "lessons/lessons-all-admin-table";
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public String allLessons(Model model) {

        List<LessonViewModel> lessons = this.lessonService
                .findAllLessons()
                .stream()
                .map(l -> this.modelMapper.map(l, LessonViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("lessons", lessons);

        return "lessons/lessons-all";
    }
}
