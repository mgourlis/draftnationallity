package gr.mgourlis.draftnationallity.dto;

import gr.mgourlis.draftnationallity.model.Exam;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class EditExamDTO {

    private long id;

    @NotEmpty
    private String localFileNumber;

    @NotEmpty
    private String foreas;

    private boolean languageExemption;

    @Size(max = 512)
    private String languageExemptionNotes;

    private boolean deaf;

    @Size(max = 512)
    private String deafNotes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocalFileNumber() {
        return localFileNumber;
    }

    public void setLocalFileNumber(String localFileNumber) {
        this.localFileNumber = localFileNumber;
    }

    public String getForeas() {
        return foreas;
    }

    public void setForeas(String foreas) {
        this.foreas = foreas;
    }

    public boolean isLanguageExemption() {
        return languageExemption;
    }

    public void setLanguageExemption(boolean languageExemption) {
        this.languageExemption = languageExemption;
    }

    public String getLanguageExemptionNotes() {
        return languageExemptionNotes;
    }

    public void setLanguageExemptionNotes(String languageExemptionNotes) {
        this.languageExemptionNotes = languageExemptionNotes;
    }

    public boolean isDeaf() {
        return deaf;
    }

    public void setDeaf(boolean deaf) {
        this.deaf = deaf;
    }

    public String getDeafNotes() {
        return deafNotes;
    }

    public void setDeafNotes(String deafNotes) {
        this.deafNotes = deafNotes;
    }

    public void init(Exam exam){
        this.setId(exam.getId());
        this.setDeaf(exam.isDeaf());
        this.setDeafNotes(exam.getDeafNotes());
        this.setForeas(exam.getForeas());
        this.setLanguageExemption(exam.isLanguageExemption());
        this.setLanguageExemptionNotes(exam.getLanguageExemptionNotes());
        this.setLocalFileNumber(exam.getLocalFileNumber());
    }
}
