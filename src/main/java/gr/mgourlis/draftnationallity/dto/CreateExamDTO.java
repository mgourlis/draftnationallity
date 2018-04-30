package gr.mgourlis.draftnationallity.dto;

import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamSetting;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CreateExamDTO {

    private long examSettingId;

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

    public long getExamSettingId() {
        return examSettingId;
    }

    public void setExamSettingId(long examSettingId) {
        this.examSettingId = examSettingId;
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
        this.setDeaf(exam.isDeaf());
        this.setDeafNotes(exam.getDeafNotes());
        if(exam.getExamSetting() != null)
            this.setExamSettingId(exam.getExamSetting().getId());
        this.setForeas(exam.getForeas());
        this.setLanguageExemption(exam.isLanguageExemption());
        this.setLanguageExemptionNotes(exam.getLanguageExemptionNotes());
        this.setLocalFileNumber(exam.getLocalFileNumber());
    }

    public Exam getExam(ExamSetting examSetting){
        Exam exam = new Exam();
        exam.setExamSetting(examSetting);
        exam.setDeaf(this.isDeaf());
        exam.setDeafNotes(this.getDeafNotes());
        exam.setForeas(this.getForeas());
        exam.setLanguageExemption(this.isLanguageExemption());
        exam.setLanguageExemptionNotes(this.getLanguageExemptionNotes());
        exam.setLocalFileNumber(this.getLocalFileNumber());
        return exam;
    }
}
