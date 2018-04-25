package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IQuestionService {
    public Question getOne(long id);
    public List<Question> findAll();
    public Page<Question> findAll(Pageable pageable);
    public Question findQuestionByShortname(String name);
    public List<Question> findQuestionsByQuestionCategoryName(String questionCategoryName);
    public Page<Question> findQuestionsByQuestionCategoryName(String questionCategoryName, Pageable pageable);
    public void save(Question question);
    public void delete(long id);
}
