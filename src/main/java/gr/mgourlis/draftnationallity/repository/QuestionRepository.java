package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long>{
    public Question findQuestionByShortnameAndDeleted(String shortname, Boolean deleted);
    public List<Question> findQuestionsByQuestionCategory_NameAndDeleted(String categoryName,Boolean deleted);
}
