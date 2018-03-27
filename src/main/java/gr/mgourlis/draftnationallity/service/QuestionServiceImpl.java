package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Question;
import gr.mgourlis.draftnationallity.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class QuestionServiceImpl {

    @Autowired
    private QuestionRepository questionRepository;

    public Question findQuestionByShortname(String shortname) { return questionRepository.findQuestionByShortname(shortname);}

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }
}
