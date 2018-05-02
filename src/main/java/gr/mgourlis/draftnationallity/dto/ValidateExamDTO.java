package gr.mgourlis.draftnationallity.dto;

import gr.mgourlis.draftnationallity.model.Exam;

import javax.validation.constraints.NotEmpty;

public class ValidateExamDTO {

    private long id;

    @NotEmpty
    private String fileNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public void init(Exam exam) {
        this.setId(exam.getId());
        this.setFileNumber(exam.getFileNumber());
    }
}
