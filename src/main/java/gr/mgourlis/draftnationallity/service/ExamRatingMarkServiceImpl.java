package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.ExamRatingMark;
import gr.mgourlis.draftnationallity.repository.ExamRatingMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("ExamRatingMarkService")
public class ExamRatingMarkServiceImpl implements IExamRatingMarkService {

    @Autowired
    ExamRatingMarkRepository examRatingMarkRepository;

    @Override
    public ExamRatingMark getOne(long id) {
        return examRatingMarkRepository.findExamRatingMarkByIdAndDeleted(id,false);
    }

    @Override
    public List<ExamRatingMark> findAll() {
        return examRatingMarkRepository.findExamRatingMarksByDeleted(false);
    }

    @Override
    public Page<ExamRatingMark> findAll(Pageable pageable) {
        return examRatingMarkRepository.findExamRatingMarksByDeleted(false, pageable);
    }

    @Override
    public ExamRatingMark findByRatingMark(String ratingMark) {
        return examRatingMarkRepository.findExamRatingMarkByRatingMarkAndDeleted(ratingMark,false);
    }

    @Override
    public void save(ExamRatingMark examRatingMark) {
        if(examRatingMark.getId() == null){
            if(examRatingMarkRepository.findExamRatingMarkByRatingMarkAndDeleted(examRatingMark.getRatingMark(),false) == null){
                examRatingMarkRepository.save(examRatingMark);
            }else{
                throw new EntityExistsException("Exam Rating Mark already exists");
            }
        }else{
            ExamRatingMark oldExamRatingMark = examRatingMarkRepository.findExamRatingMarkByIdAndDeleted(examRatingMark.getId(),false);
            if(oldExamRatingMark != null){
                oldExamRatingMark.setRatingMark(examRatingMark.getRatingMark());
                examRatingMarkRepository.save(oldExamRatingMark);
            }else{
                throw new EntityNotFoundException("Can't save Exam Rating Mark. Invalid Exam Rating Mark");
            }
        }
    }

    @Override
    public void delete(Long id) {
        ExamRatingMark examRatingMark = examRatingMarkRepository.findExamRatingMarkByIdAndDeleted(id,false);
        if(examRatingMark != null){
            examRatingMark.setDeleted(true);
            examRatingMarkRepository.save(examRatingMark);
        }else{
            throw new EntityNotFoundException("Invalid Exam Rating Mark");
        }
    }
}
