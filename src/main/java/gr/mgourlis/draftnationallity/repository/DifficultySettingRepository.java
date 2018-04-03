package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.DifficultySetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultySettingRepository extends JpaRepository<DifficultySetting,Long> {
}
