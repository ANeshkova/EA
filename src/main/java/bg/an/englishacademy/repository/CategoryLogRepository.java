package bg.an.englishacademy.repository;

import bg.an.englishacademy.model.entity.CategoryLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryLogRepository extends JpaRepository<CategoryLogEntity, Long> {
}
