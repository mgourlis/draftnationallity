package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamSetting;
import gr.mgourlis.draftnationallity.model.Question;
import gr.mgourlis.draftnationallity.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExamServiceImpl implements ExamService {

    @Autowired
    ExamRepository examRepository;


    @Override
    public Exam createExam(ExamSetting examSetting, List<Question> examQuestions) {
        return null;
    }

    @Override
    public Exam deleteExam(Exam exam) {
        return null;
    }

    @Override
    public List<Question> generateExamQuestions(ExamSetting examSetting) {
        return null;
    }

    @Override
    public Exam findLastExamByUser(String user) {
        return null;
    }

    @Override
    public List<Exam> getExamsByUser(String User) {
        return null;
    }

    @Override
    public List<Exam> getDeletedExamsByUser(String User) {
        return null;
    }

    @Override
    public List<Exam> getExams(String User) {
        return null;
    }

}
