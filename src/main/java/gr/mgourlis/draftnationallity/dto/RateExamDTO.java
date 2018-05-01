package gr.mgourlis.draftnationallity.dto;

import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamRating;

import java.util.ArrayList;
import java.util.List;

public class RateExamDTO {

    private long id;

    private List<ExamRatingDTO> examRatingsDTO;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ExamRatingDTO> getExamRatingsDTO() {
        return examRatingsDTO;
    }

    public void setExamRatingsDTO(List<ExamRatingDTO> examRatingsDTO) {
        this.examRatingsDTO = examRatingsDTO;
    }

    public void init(Exam exam){
        this.setId(exam.getId());
        List<ExamRatingDTO> examRatingsDTO = new ArrayList<>();
        for (ExamRating examRating : exam.getExamRatings()) {
            ExamRatingDTO examRatingDTO = new ExamRatingDTO();
            examRatingDTO.init(examRating);
            examRatingsDTO.add(examRatingDTO);
        }
        this.setExamRatingsDTO(examRatingsDTO);
    }

}
