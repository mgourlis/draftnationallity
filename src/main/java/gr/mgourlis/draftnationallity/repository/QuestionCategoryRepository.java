package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("QuestionCategoryRepository")
public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory,Long> {
    public List<QuestionCategory> findQuestionCategoriesByDeleted(Boolean deleted);
}
