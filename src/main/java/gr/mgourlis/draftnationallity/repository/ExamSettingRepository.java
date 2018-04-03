package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.ExamSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamSettingRepository extends JpaRepository<ExamSetting,Long> {
    public List<ExamSetting> findExamSettingsByEnabledAndDeleted(Boolean enabled, Boolean deleted);
}
