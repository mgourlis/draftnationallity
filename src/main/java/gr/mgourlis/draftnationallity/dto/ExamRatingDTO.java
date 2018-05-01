package gr.mgourlis.draftnationallity.dto;

import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamRating;

public class ExamRatingDTO {

    private long id;

    private long ratingTypeId;

    private String ratingTypeName;

    private long ratingMarkId;

    private String ratingMarkName;

    private String ratingNotes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRatingTypeId() {
        return ratingTypeId;
    }

    public void setRatingTypeId(long ratingTypeId) {
        this.ratingTypeId = ratingTypeId;
    }

    public String getRatingTypeName() {
        return ratingTypeName;
    }

    public void setRatingTypeName(String ratingTypeName) {
        this.ratingTypeName = ratingTypeName;
    }

    public long getRatingMarkId() {
        return ratingMarkId;
    }

    public void setRatingMarkId(long ratingMarkId) {
        this.ratingMarkId = ratingMarkId;
    }

    public String getRatingMarkName() {
        return ratingMarkName;
    }

    public void setRatingMarkName(String ratingMarkName) {
        this.ratingMarkName = ratingMarkName;
    }

    public String getRatingNotes() {
        return ratingNotes;
    }

    public void setRatingNotes(String ratingNotes) {
        this.ratingNotes = ratingNotes;
    }

    public void init(ExamRating examRating){
        this.setId(examRating.getId());
        if(examRating.getExamRatingMark() != null) {
            this.setRatingMarkId(examRating.getExamRatingMark().getId());
            this.setRatingMarkName(examRating.getExamRatingMark().getRatingMark());
        }
        if(examRating.getExamRatingType() != null) {
            this.setRatingTypeId(examRating.getExamRatingType().getId());
            this.setRatingTypeName(examRating.getExamRatingType().getRatingType());
        }
        this.setRatingNotes(examRating.getRatingNotes());
    }
}
