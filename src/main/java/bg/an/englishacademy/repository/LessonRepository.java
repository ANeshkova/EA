package bg.an.englishacademy.repository;

import bg.an.englishacademy.model.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

    Optional<LessonEntity> findByTitle(String title);
}
