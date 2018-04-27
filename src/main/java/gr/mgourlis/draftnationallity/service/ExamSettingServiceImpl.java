package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Difficulty;
import gr.mgourlis.draftnationallity.model.DifficultySetting;
import gr.mgourlis.draftnationallity.model.ExamSetting;
import gr.mgourlis.draftnationallity.repository.DifficultyRepository;
import gr.mgourlis.draftnationallity.repository.ExamSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("ExamSettingService")
public class ExamSettingServiceImpl implements IExamSettingService {

    @Autowired
    ExamSettingRepository examSettingRepository;

    @Autowired
    IDifficultyService difficultyService;

    @Override
    public ExamSetting getOne(long id) {
        return examSettingRepository.findExamSettingByIdAndDeleted(id,false);
    }

    @Override
    public List<ExamSetting> findAll() {
        return examSettingRepository.findExamSettingsByDeleted(false);
    }

    @Override
    public Page<ExamSetting> findAll(Pageable pageable) {
        return examSettingRepository.findExamSettingsByDeleted(false, pageable);
    }

    @Override
    public ExamSetting findExamSettingByName(String name) {
        return examSettingRepository.findExamSettingByNameAndDeleted(name,false);
    }

    @Override
    public List<ExamSetting> findExamSettingsByEnabled(boolean enabled) {
        return examSettingRepository.findExamSettingsByEnabledAndDeleted(enabled,false);
    }

    @Override
    public Page<ExamSetting> findExamSettingsByEnabled(boolean enabled, Pageable pageable) {
        return examSettingRepository.findExamSettingsByEnabledAndDeleted(enabled,false, pageable);
    }

    @Override
    public ExamSetting createExamSetting() {
        ExamSetting examSetting = new ExamSetting();
        List<Difficulty> difficulties = difficultyService.findAll();
        Set<DifficultySetting> difficultySettings = new HashSet<>();
        for (Difficulty difficulty: difficulties) {
            DifficultySetting difficultySetting = new DifficultySetting();
            difficultySetting.setDifficulty(difficulty);
            difficultySetting.setPercentage(0);
            difficultySettings.add(difficultySetting);
        }
        examSetting.setDifficultySettings(difficultySettings);
        return examSetting;
    }

    @Override
    public void save(ExamSetting examSetting) {

    }

    @Override
    public void delete(long id) {
        ExamSetting examSetting = examSettingRepository.findExamSettingByIdAndDeleted(id,false);
        if(examSetting != null){
            examSetting.setDeleted(true);
            examSettingRepository.save(examSetting);
        }else{
            throw new EntityNotFoundException("Invalid Exam Setting");
        }
    }
}
