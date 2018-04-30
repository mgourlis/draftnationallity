package gr.mgourlis.draftnationallity.dto;

import gr.mgourlis.draftnationallity.model.ExamRatingType;

import javax.validation.constraints.NotEmpty;

public class ExamRatingTypeDTO {

    private long id;

    @NotEmpty
    private String ratingType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRatingType() {
        return ratingType;
    }

    public void setRatingType(String ratingType) {
        this.ratingType = ratingType;
    }

    public void init(ExamRatingType examRatingType) throws IllegalArgumentException{
        this.setId(examRatingType.getId());
        this.setRatingType(examRatingType.getRatingType());
    }
}
