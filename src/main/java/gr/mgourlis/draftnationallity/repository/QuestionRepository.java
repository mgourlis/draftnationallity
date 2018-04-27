package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("QuestionRepository")
public interface QuestionRepository extends JpaRepository<Question,Long> {
    public List<Question> findQuestionsByDeleted(boolean deleted);
    public Page<Question> findQuestionsByDeleted(boolean deleted, Pageable pageable);
    public Question findQuestionByIdAndDeleted(long id, boolean deleted);
    public Question findQuestionByShortnameAndDeleted(String shortname, boolean deleted);
    public List<Question> findQuestionsByQuestionCategory_NameAndDeleted(String categoryName, boolean deleted);
    public Page<Question> findQuestionsByQuestionCategory_NameAndDeleted(String categoryName, boolean deleted, Pageable pageable);
    public List<Question> findQuestionsByQuestionDifficulty_LevelNumberAndDeleted(int difficultyLevelNumber, boolean deleted);
    public Page<Question> findQuestionsByQuestionDifficulty_LevelNumberAndDeleted(int difficultyLevelNumber, boolean deleted, Pageable pageable);
    public List<Question> findQuestionsByQuestionCategory_NameAndQuestionDifficulty_LevelNumberAndDeleted(String categoryName, int difficultyLevelNumber, boolean deleted);
    public Page<Question> findQuestionsByQuestionCategory_NameAndQuestionDifficulty_LevelNumberAndDeleted(String categoryName, int difficultyLevelNumber, boolean deleted, Pageable pageable);
}
