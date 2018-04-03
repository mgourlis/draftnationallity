package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory,Long> {
    public List<QuestionCategory> findQuestionCategoriesByDeleted(Boolean deleted);
}
