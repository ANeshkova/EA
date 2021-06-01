package bg.an.englishacademy.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class WordEditBindingModel {

    private String english;
    private String bulgarian;
    private String category;

    public WordEditBindingModel() {
    }

    @NotBlank(message = "English word can not be empty")
    @Size(min = 1, max = 50, message = "English word length must be between 1 and 50 characters")
    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    @NotBlank(message = "Bulgarian word can not be empty")
    @Size(min = 1, max = 100, message = "Bulgarian word length must be between 1 and 100 characters")
    public String getBulgarian() {
        return bulgarian;
    }

    public void setBulgarian(String bulgarian) {
        this.bulgarian = bulgarian;
    }

    @NotBlank(message = "Please select category")
    @Size(min = 2, max = 50)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
