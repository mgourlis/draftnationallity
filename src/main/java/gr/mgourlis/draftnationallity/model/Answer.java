package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "answers")
@AttributeOverride(name = "id", column = @Column(name = "answer_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Answer {

    @Lob
    @Column(name = "answer_text", length = 1000)
    @NotEmpty
    private String answertext;

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    private ExamQuestion examQuestion;

    public String getAnswertext() {
        return answertext;
    }

    public void setAnswertext(String answertext) {
        this.answertext = answertext;
    }

    public ExamQuestion getExamQuestion() {
        return examQuestion;
    }

    public void setExamQuestion(ExamQuestion examQuestion) {
        this.examQuestion = examQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (answertext != null ? !answertext.equals(answer.answertext) : answer.answertext != null) return false;
        return examQuestion != null ? examQuestion.equals(answer.examQuestion) : answer.examQuestion == null;
    }

    @Override
    public int hashCode() {
        int result = answertext != null ? answertext.hashCode() : 0;
        result = 31 * result + (examQuestion != null ? examQuestion.hashCode() : 0);
        return result;
    }
}
