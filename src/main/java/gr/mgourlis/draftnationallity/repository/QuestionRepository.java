package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Integer>{
    public Question findQuestionByShortname(String shortname);
}
