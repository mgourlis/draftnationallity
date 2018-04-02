package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Question;
import gr.mgourlis.draftnationallity.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

public class QuestionServiceImpl {

    @Autowired
    private QuestionRepository questionRepository;

    public Question findQuestionByShortname(String shortname) { return questionRepository.findQuestionByShortname(shortname);}

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public List<Question> shuffleQuestions(){
        List<Question> suffledquestions = new ArrayList<Question>();



        return suffledquestions;
    }
}
