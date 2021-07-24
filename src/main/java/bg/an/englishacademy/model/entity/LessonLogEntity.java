package bg.an.englishacademy.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "lessons_logs")
public class LessonLogEntity extends BaseEntity{

    private UserEntity userEntity;
    private LessonEntity lessonEntity;
    private String action;
    private LocalDateTime dateTime;

    public LessonLogEntity() {
    }

    @ManyToOne
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public LessonLogEntity setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    @ManyToOne
    public LessonEntity getLessonEntity() {
        return lessonEntity;
    }

    public LessonLogEntity setLessonEntity(LessonEntity lessonEntity) {
        this.lessonEntity = lessonEntity;
        return this;
    }

    @Column(name = "action", nullable = false)
    public String getAction() {
        return action;
    }

    public LessonLogEntity setAction(String action) {
        this.action = action;
        return this;
    }

    @Column(name = "date_time", nullable = false)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LessonLogEntity setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }
}
