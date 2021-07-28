package bg.an.englishacademy.service;

import bg.an.englishacademy.model.service.LessonLogServiceModel;

import java.util.List;

public interface LessonLogService {

    void createLog(String action, Long lessonId);

    List<LessonLogServiceModel> findAllLogs();

    void deleteLogs();
}
