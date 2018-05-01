package gr.mgourlis.draftnationallity.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "exam_rating_types")
@AttributeOverride(name = "id", column = @Column(name = "exam_rating_type_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class ExamRatingType extends BaseEntity {

    @Column(name = "rating_type")
    @NotEmpty
    private String ratingType;

    @Column(name = "language_type")
    private boolean languageType;

    public String getRatingType() {
        return ratingType;
    }

    public void setRatingType(String ratingType) {
        this.ratingType = ratingType;
    }

    public boolean isLanguageType() {
        return languageType;
    }

    public void setLanguageType(boolean languageType) {
        this.languageType = languageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamRatingType)) return false;
        if (!super.equals(o)) return false;

        ExamRatingType that = (ExamRatingType) o;

        return getRatingType() != null ? getRatingType().equals(that.getRatingType()) : that.getRatingType() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getRatingType() != null ? getRatingType().hashCode() : 0);
        return result;
    }
}
