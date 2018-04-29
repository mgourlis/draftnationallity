package gr.mgourlis.draftnationallity.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "exam_rating_marks")
@AttributeOverride(name = "id", column = @Column(name = "exam_rating_mark_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class ExamRatingMark extends BaseEntity{

    @Column(name = "rating_mark")
    @NotEmpty
    private String ratingMark;

    public String getRatingMark() {
        return ratingMark;
    }

    public void setRatingMark(String ratingMark) {
        this.ratingMark = ratingMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamRatingMark)) return false;
        if (!super.equals(o)) return false;

        ExamRatingMark that = (ExamRatingMark) o;

        return getRatingMark() != null ? getRatingMark().equals(that.getRatingMark()) : that.getRatingMark() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getRatingMark() != null ? getRatingMark().hashCode() : 0);
        return result;
    }
}
