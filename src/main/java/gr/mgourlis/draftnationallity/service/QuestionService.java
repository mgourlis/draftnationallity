package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Question;

import java.util.List;

public interface  QuestionService {
    public Question findQuestionByShortname(String shortname);
    public void saveQuestion(Question question);
    public List<Question> shuffleQuestions();
    public List<Question> findQuestionsByCategory(String categoryName);
}
