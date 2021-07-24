package bg.an.englishacademy.model.service;

import java.time.LocalDateTime;

public class LessonLogServiceModel extends BaseServiceModel {

    private String username;
    private String lessonTitle;
    private String action;
    private LocalDateTime dateTime;

    public LessonLogServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public LessonLogServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public LessonLogServiceModel setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
        return this;
    }

    public String getAction() {
        return action;
    }

    public LessonLogServiceModel setAction(String action) {
        this.action = action;
        return this;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LessonLogServiceModel setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }
}
