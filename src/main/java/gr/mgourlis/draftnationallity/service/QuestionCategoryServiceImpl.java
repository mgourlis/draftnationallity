package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.QuestionCategory;
import gr.mgourlis.draftnationallity.repository.QuestionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class QuestionCategoryServiceImpl implements IQuestionCategoryService {

    @Autowired
    QuestionCategoryRepository questionCategoryRepository;

    @Override
    public QuestionCategory getOne(long id) {
        return questionCategoryRepository.findQuestionCategoryByIdAndDeleted(id,false);
    }

    @Override
    public List<QuestionCategory> findAll() {
        return questionCategoryRepository.findQuestionCategoriesByDeleted(false);
    }

    @Override
    public Page<QuestionCategory> findAll(Pageable pageable) {
        return questionCategoryRepository.findQuestionCategoriesByDeleted(false, pageable);
    }

    @Override
    public QuestionCategory findQuestionCategoryByName(String name) {
        return questionCategoryRepository.findQuestionCategoryByNameAndDeleted(name, false);
    }

    @Override
    public void save(QuestionCategory questionCategory) {
        if(questionCategory.getId() == null){
            if(questionCategoryRepository.findQuestionCategoryByNameAndDeleted(questionCategory.getName(),false) == null){
                questionCategoryRepository.save(questionCategory);
            }else{
                throw new IllegalArgumentException("Question Category already exists");
            }
        }else{
            QuestionCategory oldQuestionCategory = questionCategoryRepository.getOne(questionCategory.getId());
            if(oldQuestionCategory != null){
                oldQuestionCategory.setName(questionCategory.getName());
                oldQuestionCategory.setQuestions(questionCategory.getQuestions());
                questionCategoryRepository.save(oldQuestionCategory);
            }else{
                throw new EntityNotFoundException("Can't save Question Category. Invalid Question Category");
            }
        }

    }

    @Override
    public void delete(long id) {
        QuestionCategory questionCategory = questionCategoryRepository.getOne(id);
        if(questionCategory != null){
            questionCategory.setName(questionCategory.getName()+" (deleted)");
            questionCategory.setDeleted(true);
            save(questionCategory);
        }else{
            throw new EntityNotFoundException("Invalid Question Category");
        }
    }
}
