package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "question")
@AttributeOverride(name = "id", column = @Column(name = "question_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Question extends BaseEntity {

    @Column(name = "short_name", unique=true)
    @NotNull(message = "*Please provide a short name for the question")
    private String shortname;

    @Lob
    @Column(name = "question_text", length=1000)
    @NotNull(message = "*The question's text can not be empty")
    private String questiontext;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="question_category_id",referencedColumnName="question_category_id")
    private QuestionCategory questionCategory;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="difficulty_id",referencedColumnName="difficulty_id")
    private Difficulty questionDifficulty;

    @ManyToMany
    @JoinTable(
            name="question_packet",
            joinColumns=@JoinColumn(name="question_id", referencedColumnName="question_id"),
            inverseJoinColumns=@JoinColumn(name="exam_id", referencedColumnName="exam_id"))
    private List<Exam> exams;

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getQuestiontext() {
        return questiontext;
    }

    public void setQuestiontext(String questiontext) {
        this.questiontext = questiontext;
    }

    public QuestionCategory getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(QuestionCategory questionCategory) {
        this.questionCategory = questionCategory;
    }

    public Difficulty getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(Difficulty questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }
}
