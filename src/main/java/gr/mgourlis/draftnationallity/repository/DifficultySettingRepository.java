package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.DifficultySetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("DifficultySettingRepository")
public interface DifficultySettingRepository extends JpaRepository<DifficultySetting,Long> {
}
