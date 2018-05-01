package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.dto.EditExamDTO;
import gr.mgourlis.draftnationallity.dto.ExamQuestionDTO;
import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamQuestion;
import gr.mgourlis.draftnationallity.model.ExamRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IExamService {
    public Exam getOne(long id);
    public Exam getOneByUser(long id, String email);
    public Exam findByUID(String uID);
    public Exam findByLocalFileNumber(String localFileNumber);
    public Exam findByFileNumber(String fileNumber);
    public List<Exam> findAll();
    public Page<Exam> findAll(Pageable pageable);
    public List<Exam> findExamsByUser(String email);
    public Page<Exam> findExamsByUser(String email, Pageable pageable);
    public String createExam(Exam exam, long examSettingId);
    public void editExam(Exam exam, EditExamDTO editExamDTO);
    public void setExamAnswers(Exam exam, List<ExamQuestionDTO> examQuestionsDTO, boolean finalAnswers);
    public void rateExam(Exam exam, List<ExamRating> examRatings);
    public void validateExam(Exam exam);
    public void delete(long id);

}
