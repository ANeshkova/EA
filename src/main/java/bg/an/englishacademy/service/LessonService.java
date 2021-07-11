package bg.an.englishacademy.service;

import bg.an.englishacademy.model.service.LessonServiceModel;

import java.util.List;

public interface LessonService {
    LessonServiceModel addLesson(LessonServiceModel lessonServiceModel);

    boolean lessonTitleExists(String title);

    List<LessonServiceModel> findAllLessons();
}
