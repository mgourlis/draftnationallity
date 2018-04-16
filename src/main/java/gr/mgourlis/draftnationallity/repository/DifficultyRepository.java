package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("DifficultyRepository")
public interface DifficultyRepository extends JpaRepository<Difficulty,Long> {
}
