package bg.an.englishacademy.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "words")
public class WordEntity extends BaseEntity {

    private String english;
    private String bulgarian;
    private CategoryEntity category;

    public WordEntity() {
    }

    @Column(name = "english", nullable = false)
    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    @Column(name = "bulgarian", nullable = false)
    public String getBulgarian() {
        return bulgarian;
    }

    public void setBulgarian(String bulgarian) {
        this.bulgarian = bulgarian;
    }

    @ManyToOne
    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
