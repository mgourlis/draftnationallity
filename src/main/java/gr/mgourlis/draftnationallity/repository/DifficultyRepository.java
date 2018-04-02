package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyRepository extends JpaRepository<Difficulty,Long> {
}
