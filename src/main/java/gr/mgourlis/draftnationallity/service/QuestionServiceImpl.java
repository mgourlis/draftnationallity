package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Question;
import gr.mgourlis.draftnationallity.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question findQuestionByShortname(String shortname) { return questionRepository.findQuestionByShortnameAndDeleted(shortname,false);}

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Question question) {

    }

    @Override
    public List<Question> shuffleQuestions() {
        return null;
    }

    @Override
    public List<Question> findQuestionsByCategory(String categoryName) {
        return null;
    }

}
