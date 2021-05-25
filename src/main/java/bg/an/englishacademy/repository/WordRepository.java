package bg.an.englishacademy.repository;

import bg.an.englishacademy.model.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long> {

    Optional<WordEntity> findByEnglishAndCategory_Name(String word, String categoryName);
}
