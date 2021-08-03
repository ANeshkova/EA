package bg.an.englishacademy.schedule;

import bg.an.englishacademy.service.CategoryLogService;
import bg.an.englishacademy.service.LessonLogService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduler {

    private final CategoryLogService categoryLogService;
    private final LessonLogService lessonLogService;

    public MyScheduler(CategoryLogService categoryLogService, LessonLogService lessonLogService) {
        this.categoryLogService = categoryLogService;
        this.lessonLogService = lessonLogService;
    }

    @Scheduled(cron = "0 0 16 * * MON-FRI", zone = "Europe/Sofia")
    public void deleteLogs() {
        this.categoryLogService.deleteLogs();
        this.lessonLogService.deleteLogs();
    }
}
