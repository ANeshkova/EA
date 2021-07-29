package bg.an.englishacademy.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "categories_logs")
public class CategoryLogEntity extends BaseEntity{

    private UserEntity userEntity;
    private CategoryEntity categoryEntity;
    private String action;
    private LocalDateTime dateTime;

    public CategoryLogEntity() {
    }

    @ManyToOne
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public CategoryLogEntity setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    @ManyToOne
    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public CategoryLogEntity setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
        return this;
    }

    @Column(name = "action", nullable = false)
    public String getAction() {
        return action;
    }

    public CategoryLogEntity setAction(String action) {
        this.action = action;
        return this;
    }

    @Column(name = "date_time", nullable = false)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public CategoryLogEntity setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }
}
