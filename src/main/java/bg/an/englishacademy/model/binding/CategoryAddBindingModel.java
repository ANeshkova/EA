package bg.an.englishacademy.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryAddBindingModel {

    private String name;
    private MultipartFile imageUrl;

    public CategoryAddBindingModel() {
    }

    @NotBlank(message = "Category name can not be empty")
    @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }
}
