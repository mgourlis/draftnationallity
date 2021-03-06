package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.ExamSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IExamSettingService {
    public ExamSetting getOne(long id);
    public List<ExamSetting> findAll();
    public Page<ExamSetting> findAll(Pageable pageable);
    public ExamSetting findExamSettingByName(String name);
    public List<ExamSetting> findExamSettingsByEnabled(boolean enabled);
    public Page<ExamSetting> findExamSettingsByEnabled(boolean enabled, Pageable pageable);
    public ExamSetting createExamSetting();
    public void save(ExamSetting examSetting);
    public void delete(long id);
}
