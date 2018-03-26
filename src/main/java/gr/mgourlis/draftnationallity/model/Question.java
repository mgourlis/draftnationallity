package gr.mgourlis.draftnationallity.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_category_id")
    private int id;

    @Column(name = "short_name")
    @NotEmpty(message = "*Please provide a short name for the question")
    private String shortname;

    @Lob
    @Column(name = "question_text", length=1000)
    @NotEmpty(message = "*The question's text can not be empty")
    private String questiontext;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="question_category_id",referencedColumnName="question_category_id")
    private QuestionCategory questionCategory;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="difficulty_id",referencedColumnName="difficulty_id")
    private Difficulty questionDifficulty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
