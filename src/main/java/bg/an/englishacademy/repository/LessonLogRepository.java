package bg.an.englishacademy.repository;

import bg.an.englishacademy.model.entity.LessonLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonLogRepository extends JpaRepository<LessonLogEntity, Long> {
}
