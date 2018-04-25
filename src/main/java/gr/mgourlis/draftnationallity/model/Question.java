package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "question")
@AttributeOverride(name = "id", column = @Column(name = "question_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Question extends BaseEntity {

    @Column(name = "short_name")
    @NotNull(message = "*Please provide a short name for the question")
    private String shortname;

    @Lob
    @Column(name = "question_text", length=1000)
    @NotNull(message = "*The question's text can not be empty")
    private String questiontext;

    @ManyToOne(optional=false, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="question_category_id",referencedColumnName="question_category_id")
    private QuestionCategory questionCategory;

    @ManyToOne(optional=false, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="difficulty_id",referencedColumnName="difficulty_id")
    private Difficulty questionDifficulty;

    @ManyToMany(fetch = FetchType.LAZY)
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
        if(this.questionCategory != null){
            this.questionCategory.getQuestions().remove(this);
        }
        this.questionCategory = questionCategory;
    }

    public Difficulty getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(Difficulty questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        if (!super.equals(o)) return false;

        Question question = (Question) o;

        if (!getShortname().equals(question.getShortname())) return false;
        return getQuestiontext().equals(question.getQuestiontext());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getShortname().hashCode();
        result = 31 * result + getQuestiontext().hashCode();
        return result;
    }
}
