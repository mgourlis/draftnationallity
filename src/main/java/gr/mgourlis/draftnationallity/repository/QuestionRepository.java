package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("QuestionRepository")
public interface QuestionRepository extends JpaRepository<Question,Long> {
    public Question findQuestionByShortnameAndDeleted(String shortname, Boolean deleted);
    public List<Question> findQuestionsByQuestionCategory_NameAndDeleted(String categoryName, Boolean deleted);
}
