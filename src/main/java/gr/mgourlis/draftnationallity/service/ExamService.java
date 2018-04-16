package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamSetting;
import gr.mgourlis.draftnationallity.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExamService {

    public Exam createExam(ExamSetting examSetting, List<Question> examQuestions);
    public Exam deleteExam(Exam exam);
    public List<Question> generateExamQuestions(ExamSetting examSetting);
    public Exam findLastExamByUser(String user);
    public List<Exam> getExamsByUser(String User);
    public List<Exam> getDeletedExamsByUser(String User);
    public List<Exam> getExams(String User);

}
