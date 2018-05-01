package gr.mgourlis.draftnationallity.dto;

import gr.mgourlis.draftnationallity.constraints.FieldMatch;
import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamQuestion;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class AnswerExamDTO {

    private long id;

    @NotEmpty
    private List<ExamQuestionDTO> examQuestionsDTO;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ExamQuestionDTO> getExamQuestionsDTO() {
        return examQuestionsDTO;
    }

    public void setExamQuestionsDTO(List<ExamQuestionDTO> examQuestionsDTO) {
        this.examQuestionsDTO = examQuestionsDTO;
    }

    public void init(Exam exam){
        this.setId(exam.getId());
        List<ExamQuestionDTO> examQuestionsDTO = new ArrayList<>();
        for (ExamQuestion examQuestion : exam.getExamQuestions()) {
            ExamQuestionDTO examQuestionDTO = new ExamQuestionDTO();
            examQuestionDTO.init(examQuestion);
            examQuestionsDTO.add(examQuestionDTO);
        }
        this.setExamQuestionsDTO(examQuestionsDTO);
    }
}
