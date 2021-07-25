package bg.an.englishacademy.service;

import bg.an.englishacademy.model.service.LessonServiceModel;

import java.util.List;

public interface LessonService {
    LessonServiceModel addLesson(LessonServiceModel lessonServiceModel);

    boolean lessonTitleExists(String title);

    List<LessonServiceModel> findAllLessons();

    LessonServiceModel findLessonById(Long id);

    LessonServiceModel editLesson(Long id, LessonServiceModel lessonServiceModel);

    void deleteLesson(Long id);

    LessonServiceModel findLessonByTitle(String lessonTitle);
}
