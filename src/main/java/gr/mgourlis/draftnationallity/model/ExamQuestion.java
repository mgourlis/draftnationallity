package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;

@Entity
@Table(name = "examquestions")
@AttributeOverride(name = "id", column = @Column(name = "exam_question_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class ExamQuestion extends BaseEntity implements Comparable<ExamQuestion>{

    @Column(name = "short_number")
    private int sortNumber;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="question_id",referencedColumnName="question_id")
    private Question question;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="answer_id")
    private Answer answer;

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamQuestion that = (ExamQuestion) o;

        if (sortNumber != that.sortNumber) return false;
        if (question != null ? !question.equals(that.question) : that.question != null) return false;
        return answer != null ? answer.equals(that.answer) : that.answer == null;
    }

    @Override
    public int hashCode() {
        int result = sortNumber;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(ExamQuestion o) {
        if(this.sortNumber > o.sortNumber){
            return 1;
        }else if(this.sortNumber < o.sortNumber){
            return -1;
        }else{
            return 0;
        }
    }
}
