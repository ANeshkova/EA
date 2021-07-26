package bg.an.englishacademy.service;

public interface LessonLogService {

    void createLog(String action, Long lessonId);
}
