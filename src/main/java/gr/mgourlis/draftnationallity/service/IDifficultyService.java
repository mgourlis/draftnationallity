package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Difficulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDifficultyService {
    public Difficulty findDifficultyById(long id);
    public List<Difficulty> findAll();
    public Page<Difficulty> findAll(Pageable pageable);
    public  Difficulty findByLevel(String level);
    public  Difficulty findByLevelNumber(int levelNumber);
    public void save(Difficulty difficulty);
    public void delete(Long id);

}
