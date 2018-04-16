package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface  QuestionService {
    public Question findQuestionByShortname(String shortname);
    public void saveQuestion(Question question);
    public void deleteQuestion(Question question);
    public List<Question> shuffleQuestions();
    public List<Question> findQuestionsByCategory(String categoryName);
}
