package bg.an.englishacademy.model.service;

public class WordServiceModel extends BaseServiceModel {

    private String english;
    private String bulgarian;
    private CategoryServiceModel category;

    public WordServiceModel() {
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getBulgarian() {
        return bulgarian;
    }

    public void setBulgarian(String bulgarian) {
        this.bulgarian = bulgarian;
    }

    public CategoryServiceModel getCategory() {
        return category;
    }

    public void setCategory(CategoryServiceModel category) {
        this.category = category;
    }
}
