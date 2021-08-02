package bg.an.englishacademy.web.controllers;

import bg.an.englishacademy.service.CategoryLogService;
import bg.an.englishacademy.service.LessonLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatsController {

    private final CategoryLogService categoryLogService;
    private final LessonLogService lessonLogService;

    public StatsController(CategoryLogService categoryLogService, LessonLogService lessonLogService) {
        this.categoryLogService = categoryLogService;
        this.lessonLogService = lessonLogService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String stats(Model model){
        model.addAttribute("categoryLogs", categoryLogService.findAllLogs());
        model.addAttribute("lessonLogs", lessonLogService.findAllLogs());
        return "stats";
    }
}
