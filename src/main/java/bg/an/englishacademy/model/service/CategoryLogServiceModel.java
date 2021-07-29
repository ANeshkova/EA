package bg.an.englishacademy.model.service;

import java.time.LocalDateTime;

public class CategoryLogServiceModel extends BaseServiceModel {

    private String username;
    private String category;
    private String action;
    private LocalDateTime dateTime;

    public CategoryLogServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public CategoryLogServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public CategoryLogServiceModel setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getAction() {
        return action;
    }

    public CategoryLogServiceModel setAction(String action) {
        this.action = action;
        return this;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public CategoryLogServiceModel setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }
}
