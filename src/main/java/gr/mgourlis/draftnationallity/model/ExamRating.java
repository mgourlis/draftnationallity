package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;

@Entity
@Table(name = "exam_ratings")
@AttributeOverride(name = "id", column = @Column(name = "exam_rating_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class ExamRating extends BaseEntity {

    @Column(name = "rating_notes")
    private String ratingNotes;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "exam_rating_type_id", referencedColumnName = "exam_rating_type_id", nullable = false)
    private ExamRatingType examRatingType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "exam_rating_mark_id", referencedColumnName = "exam_rating_mark_id", nullable = false)
    private ExamRatingMark examRatingMark;

    public String getRatingNotes() {
        return ratingNotes;
    }

    public void setRatingNotes(String ratingNotes) {
        this.ratingNotes = ratingNotes;
    }

    public ExamRatingType getExamRatingType() {
        return examRatingType;
    }

    public void setExamRatingType(ExamRatingType examRatingType) {
        this.examRatingType = examRatingType;
    }

    public ExamRatingMark getExamRatingMark() {
        return examRatingMark;
    }

    public void setExamRatingMark(ExamRatingMark examRatingMark) {
        this.examRatingMark = examRatingMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamRating)) return false;

        ExamRating that = (ExamRating) o;

        if (getRatingNotes() != null ? !getRatingNotes().equals(that.getRatingNotes()) : that.getRatingNotes() != null)
            return false;
        if (getExamRatingType() != null ? !getExamRatingType().equals(that.getExamRatingType()) : that.getExamRatingType() != null)
            return false;
        return getExamRatingMark() != null ? getExamRatingMark().equals(that.getExamRatingMark()) : that.getExamRatingMark() == null;
    }

    @Override
    public int hashCode() {
        int result = getRatingNotes() != null ? getRatingNotes().hashCode() : 0;
        result = 31 * result + (getExamRatingType() != null ? getExamRatingType().hashCode() : 0);
        result = 31 * result + (getExamRatingMark() != null ? getExamRatingMark().hashCode() : 0);
        return result;
    }

}
