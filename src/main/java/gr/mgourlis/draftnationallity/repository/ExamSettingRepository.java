package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.ExamSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ExamSettingRepository")
public interface ExamSettingRepository extends JpaRepository<ExamSetting,Long> {
    public List<ExamSetting> findExamSettingsByEnabledAndDeleted(Boolean enabled, Boolean deleted);
}
