package bg.an.englishacademy.web.controllers;

import bg.an.englishacademy.service.LessonLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatsController {

    private final LessonLogService lessonLogService;

    public StatsController(LessonLogService lessonLogService) {
        this.lessonLogService = lessonLogService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String stats(Model model){
        model.addAttribute("lessonLogs", lessonLogService.findAllLogs());
        return "stats";
    }
}
