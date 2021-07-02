package bg.an.englishacademy.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LessonEditBindingModel {

    private String title;
    private String description;
    private String videoUrl;

    public LessonEditBindingModel() {
    }

    @NotBlank(message = "Title can not be empty")
    @Size(min = 3, max = 100, message = "Title length must be between 3 and 100 characters")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank(message = "Description can not be empty")
    @Size(min = 3, message = "Description length must be minimum 3 characters")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotBlank(message = "Video url can not be empty")
    @Size(min = 11, max = 11, message = "Video url length must be 11 characters")
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
