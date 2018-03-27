package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Question;

public interface  QuestionService {
    public Question findQuestionByShortname(String shortname);
    public void saveQuestion(Question question);
}
