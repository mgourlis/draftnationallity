package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamSetting;
import gr.mgourlis.draftnationallity.model.Question;

import java.util.List;

public class ExamServiceImpl implements ExamService {
    @Override
    public Exam createExam(ExamSetting examSetting, List<Question> examQuestions) {
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
    public List<Exam> getExams() {
        return null;
    }
}
