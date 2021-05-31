package bg.an.englishacademy.model.view;

public class WordViewModel {

    private Long id;
    private String english;
    private String bulgarian;
    private String category;

    public WordViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
