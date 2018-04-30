package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.ExamRatingMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IExamRatingMarkService {
    public ExamRatingMark getOne(long id);
    public List<ExamRatingMark> findAll();
    public Page<ExamRatingMark> findAll(Pageable pageable);
    public ExamRatingMark findByRatingMark(String ratingMark);
    public void save(ExamRatingMark examRatingMark);
    public void delete(Long id);
}
