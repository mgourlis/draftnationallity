package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "questioncategory")
@AttributeOverride(name = "id", column = @Column(name = "question_category_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class QuestionCategory extends BaseEntity {

    @Column(name = "name")
    @NotNull(message = "*Please provide a name for the category")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionCategory)) return false;
        if (!super.equals(o)) return false;

        QuestionCategory that = (QuestionCategory) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }
}
