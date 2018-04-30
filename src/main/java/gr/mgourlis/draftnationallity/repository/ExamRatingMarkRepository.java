package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.ExamRatingMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ExamRatingMarkRepository")
public interface ExamRatingMarkRepository extends JpaRepository<ExamRatingMark,Long> {
    List<ExamRatingMark> findExamRatingMarksByDeleted(Boolean deleted);
    Page<ExamRatingMark> findExamRatingMarksByDeleted(Boolean deleted, Pageable pageable);
    ExamRatingMark findExamRatingMarkByIdAndDeleted(long id, boolean deleted);
    ExamRatingMark findExamRatingMarkByRatingMarkAndDeleted(String ratingMark, boolean deleted);
}
