package gr.mgourlis.draftnationallity.dto;

import gr.mgourlis.draftnationallity.model.ExamQuestion;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ExamQuestionDTO {

    private long id;

    private int sortNumber;

    private String Questiontext;

    @NotEmpty
    private String Answertext;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getQuestiontext() {
        return Questiontext;
    }

    public void setQuestiontext(String questiontext) {
        Questiontext = questiontext;
    }

    @Size(max = 512)
    @NotEmpty
    public String getAnswertext() {
        return Answertext;
    }

    public void setAnswertext(String answertext) {
        Answertext = answertext;
    }

    public void init(ExamQuestion examQuestion){
        this.setId(examQuestion.getId());
        this.setSortNumber(examQuestion.getSortNumber());
        if(examQuestion.getQuestion() != null)
            this.setQuestiontext(examQuestion.getQuestion().getQuestiontext());
        if(examQuestion.getAnswer() != null)
            this.setAnswertext(examQuestion.getAnswer().getAnswertext());
    }
}
