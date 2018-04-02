package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory,Long> {
}
