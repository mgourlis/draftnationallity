package gr.mgourlis.draftnationallity.dto;

import gr.mgourlis.draftnationallity.model.ExamRatingMark;

import javax.validation.constraints.NotEmpty;

public class ExamRatingMarkDTO {

    private long id;

    @NotEmpty
    private String ratingMark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRatingMark() {
        return ratingMark;
    }

    public void setRatingMark(String ratingMark) {
        this.ratingMark = ratingMark;
    }

    public void init(ExamRatingMark examRatingMark) throws IllegalArgumentException{
        this.setId(examRatingMark.getId());
        this.setRatingMark(examRatingMark.getRatingMark());
    }
}
