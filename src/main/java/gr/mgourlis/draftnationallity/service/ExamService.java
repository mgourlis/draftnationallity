package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamSetting;
import gr.mgourlis.draftnationallity.model.Question;

import java.util.List;

public interface ExamService {

    public Exam createExam(ExamSetting examSetting, List<Question> examQuestions);
    public List<Question> generateExamQuestions(ExamSetting examSetting);
    public Exam findLastExamByUser(String user);
    public List<Exam> getExams();

}
