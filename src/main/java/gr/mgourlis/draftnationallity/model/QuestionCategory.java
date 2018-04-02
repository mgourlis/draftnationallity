package gr.mgourlis.draftnationallity.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questioncategory")
@AttributeOverride(name = "id", column = @Column(name = "question_category_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class QuestionCategory extends BaseEntity {

    @Column(name = "name", unique=true)
    @NotEmpty(message = "*Please provide a name for the category")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "questionCategory")
    private List<Question> questions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
