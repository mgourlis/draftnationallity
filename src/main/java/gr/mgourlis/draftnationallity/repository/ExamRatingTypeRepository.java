package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.ExamRatingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ExamRatingTypeRepository")
public interface ExamRatingTypeRepository extends JpaRepository<ExamRatingType,Long> {
    List<ExamRatingType> findExamRatingTypesByDeleted(Boolean deleted);
    Page<ExamRatingType> findExamRatingTypesByDeleted(Boolean deleted, Pageable pageable);
    ExamRatingType findExamRatingTypeByIdAndDeleted(long id, boolean deleted);
    ExamRatingType findExamRatingTypeByRatingTypeAndDeleted(String ratingType, boolean deleted);

}
