package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Question;
import gr.mgourlis.draftnationallity.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("QuestionService")
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public Question getOne(long id) {
        return questionRepository.findQuestionByIdAndDeleted(id,false);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findQuestionsByDeleted(false);
    }

    @Override
    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findQuestionsByDeleted(false, pageable);
    }

    @Override
    public Question findQuestionByShortname(String name) {
        return questionRepository.findQuestionByShortnameAndDeleted(name, false);
    }

    @Override
    public List<Question> findQuestionsByQuestionCategoryName(String questionCategoryName) {
        return questionRepository.findQuestionsByQuestionCategory_NameAndDeleted(questionCategoryName,false);
    }

    @Override
    public Page<Question> findQuestionsByQuestionCategoryName(String questionCategoryName, Pageable pageable) {
        return questionRepository.findQuestionsByQuestionCategory_NameAndDeleted(questionCategoryName,false, pageable);
    }

    @Override
    public void save(Question question) {
        if(question.getId() == null){
            if(questionRepository.findQuestionByShortnameAndDeleted(question.getShortname(),false) == null){
                questionRepository.save(question);
            }else{
                throw new EntityExistsException("Difficulty already exists");
            }
        }else{
            Question oldQuestion = questionRepository.findQuestionByIdAndDeleted(question.getId(),false);
            if(oldQuestion != null){
                oldQuestion.setShortname(question.getShortname());
                oldQuestion.setQuestiontext(question.getQuestiontext());
                oldQuestion.setQuestionCategory(question.getQuestionCategory());
                oldQuestion.setQuestionDifficulty(question.getQuestionDifficulty());
                questionRepository.save(oldQuestion);
            }else{
                throw new EntityNotFoundException("Can't save difficulty. Invalid difficulty");
            }
        }
    }

    @Override
    public void delete(long id) {
        Question question = questionRepository.findQuestionByIdAndDeleted(id,false);
        if(question != null){
            question.setDeleted(true);
            questionRepository.save(question);
        }else{
            throw new EntityNotFoundException("Invalid Question");
        }
    }
}
