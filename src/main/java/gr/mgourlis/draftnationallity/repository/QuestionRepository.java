package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long>{
    public Question findQuestionByShortname(String shortname);
    public List<Question> findQuestionsByQuestionCategory_Name(String categoryName);
}
