package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.ExamRatingType;
import gr.mgourlis.draftnationallity.repository.ExamRatingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("ExamRatingTypeService")
public class ExamRatingTypeServiceImpl implements IExamRatingTypeService {
    @Autowired
    ExamRatingTypeRepository examRatingTypeRepository;

    @Override
    public ExamRatingType getOne(long id) {
        return examRatingTypeRepository.findExamRatingTypeByIdAndDeleted(id,false);
    }

    @Override
    public List<ExamRatingType> findAll() {
        return examRatingTypeRepository.findExamRatingTypesByDeleted(false);
    }

    @Override
    public Page<ExamRatingType> findAll(Pageable pageable) {
        return examRatingTypeRepository.findExamRatingTypesByDeleted(false, pageable);
    }

    @Override
    public ExamRatingType findByRatingType(String ratingType) {
        return examRatingTypeRepository.findExamRatingTypeByRatingTypeAndDeleted(ratingType,false);
    }

    @Override
    public void save(ExamRatingType examRatingType) {
        if(examRatingType.getId() == null){
            if(examRatingTypeRepository.findExamRatingTypeByRatingTypeAndDeleted(examRatingType.getRatingType(),false) == null){
                examRatingTypeRepository.save(examRatingType);
            }else{
                throw new EntityExistsException("Exam Rating Type already exists");
            }
        }else{
            ExamRatingType oldExamRatingType = examRatingTypeRepository.findExamRatingTypeByIdAndDeleted(examRatingType.getId(),false);
            if(oldExamRatingType != null){
                oldExamRatingType.setRatingType(examRatingType.getRatingType());
                examRatingTypeRepository.save(oldExamRatingType);
            }else{
                throw new EntityNotFoundException("Can't save Exam Rating Type. Invalid Exam Rating Type");
            }
        }
    }

    @Override
    public void delete(Long id) {
        ExamRatingType examRatingType = examRatingTypeRepository.findExamRatingTypeByIdAndDeleted(id,false);
        if(examRatingType != null){
            examRatingType.setDeleted(true);
            examRatingTypeRepository.save(examRatingType);
        }else{
            throw new EntityNotFoundException("Invalid Exam Rating Type");
        }
    }
}
