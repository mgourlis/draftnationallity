package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.DifficultySettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultySettingsRepository extends JpaRepository<DifficultySettings,Long> {
}
