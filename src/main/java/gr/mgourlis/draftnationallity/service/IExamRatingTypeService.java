package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.ExamRatingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IExamRatingTypeService {
    public ExamRatingType getOne(long id);
    public List<ExamRatingType> findAll();
    public Page<ExamRatingType> findAll(Pageable pageable);
    public ExamRatingType findByRatingType(String ratingType);
    public void save(ExamRatingType examRatingType);
    public void delete(Long id);
}
