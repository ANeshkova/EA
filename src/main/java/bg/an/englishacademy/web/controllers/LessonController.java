package bg.an.englishacademy.web.controllers;

import bg.an.englishacademy.model.binding.LessonAddBindingModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/lessons")
public class LessonController extends BaseController{

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addLesson(Model model) {

        if (!model.containsAttribute("lessonAddBindingModel")) {
            model.addAttribute("lessonAddBindingModel", new LessonAddBindingModel());
        }

        return "lessons/lesson-add";
    }
}
