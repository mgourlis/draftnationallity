package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "answers")
@AttributeOverride(name = "id", column = @Column(name = "answer_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Answer extends BaseEntity {

    @Lob
    @Column(name = "answer_text", length = 1000)
    @NotEmpty
    private String answertext;

    public String getAnswertext() {
        return answertext;
    }

    public void setAnswertext(String answertext) {
        this.answertext = answertext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        if (!super.equals(o)) return false;

        Answer answer = (Answer) o;

        return getAnswertext() != null ? getAnswertext().equals(answer.getAnswertext()) : answer.getAnswertext() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getAnswertext() != null ? getAnswertext().hashCode() : 0);
        return result;
    }
}
