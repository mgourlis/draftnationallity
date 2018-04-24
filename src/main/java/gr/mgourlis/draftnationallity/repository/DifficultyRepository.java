package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.Difficulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("DifficultyRepository")
public interface DifficultyRepository extends JpaRepository<Difficulty,Long> {
    Page<Difficulty> findDifficultiesByDeleted(Boolean deleted, Pageable pageable);
    Difficulty findDifficultyByLevelAndDeleted(String level, Boolean deleted);
    Difficulty findDifficultyByLevelNumberAndDeleted(int levelNumber, Boolean deleted);
}
