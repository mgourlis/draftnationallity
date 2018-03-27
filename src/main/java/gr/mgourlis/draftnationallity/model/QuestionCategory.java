package gr.mgourlis.draftnationallity.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questioncategory")
public class QuestionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_category_id")
    private int id;

    @Column(name = "name", unique=true)
    @NotEmpty(message = "*Please provide a name for the category")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "questionCategory")
    private List<Question> questions;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
