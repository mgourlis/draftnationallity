package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.dto.CommitteeMemberDTO;
import gr.mgourlis.draftnationallity.dto.EditExamDTO;
import gr.mgourlis.draftnationallity.dto.ExamQuestionDTO;
import gr.mgourlis.draftnationallity.dto.ExamRatingDTO;
import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamStatus;
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
    public List<Exam> findExamsByLocalFileNumberAndUser(String localFileNumber, String email);
    public Page<Exam> findExamsByLocalFileNumberAndUser(String localFileNumber, String email, Pageable pageable);
    public List<Exam> findAllByStatus(ExamStatus status);
    public Page<Exam> findAllByStatus(ExamStatus status, Pageable pageable);
    public List<Exam> findExamsByLocalFileNumberContainingOrFileNumberContainingOrUIDContainingAndStatus(String localFileNumber, String FileNumber, String uID, ExamStatus status);
    public Page<Exam> findExamsByLocalFileNumberContainingOrFileNumberContainingOrUIDContainingAndStatus(String localFileNumber, String FileNumber, String uID, ExamStatus status, Pageable pageable);
    public String createExam(Exam exam, long examSettingId);
    public void editExam(Exam exam, EditExamDTO editExamDTO);
    public void setExamAnswers(Exam exam, List<ExamQuestionDTO> examQuestionsDTO, boolean finalAnswers);
    public void rateExam(Exam exam, List<ExamRatingDTO> examRatingsDTO, boolean finalRatings);
    public void finalizeExam(Exam exam, List<CommitteeMemberDTO> committeeMembersDTO, boolean finalize);
    public void validateExam(Exam exam, String fileNumber, String email, boolean finalValidation);
    public void delete(long id);

}
