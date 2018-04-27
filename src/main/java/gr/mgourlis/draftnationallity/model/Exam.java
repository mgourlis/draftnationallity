package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "exam")
@AttributeOverride(name = "id", column = @Column(name = "exam_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Exam extends BaseEntity {

    @Column(name = "uID", unique = true)
    @NotEmpty
    private String uID;

    @Column(name = "local_file_number")
    @NotEmpty
    private String localFileNumber;

    @Column(name = "file_number")
    private String fileNumber;

    @Column(name = "foreas")
    @NotEmpty
    private String foreas;

    @Column(name = "rated_date")
    @Temporal(TemporalType.DATE)
    private Date ratedDate;

    @Column(name = "validated")
    private Boolean validated;

    @Column(name = "validated_date")
    @Temporal(TemporalType.DATE)
    private Boolean validatedDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "exam_setting_id", referencedColumnName = "exam_setting_id", nullable = false)
    private ExamSetting examSetting;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "exam_id", referencedColumnName = "exam_id", nullable = false)
    private List<ExamQuestion> examQuestions;

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getLocalFileNumber() {
        return localFileNumber;
    }

    public void setLocalFileNumber(String localFileNumber) {
        this.localFileNumber = localFileNumber;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getForeas() {
        return foreas;
    }

    public void setForeas(String foreas) {
        this.foreas = foreas;
    }

    public Date getRatedDate() {
        return ratedDate;
    }

    public void setRatedDate(Date rateddDate) {
        this.ratedDate = rateddDate;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public Boolean getValidatedDate() {
        return validatedDate;
    }

    public void setValidatedDate(Boolean validatedDate) {
        this.validatedDate = validatedDate;
    }

    public ExamSetting getExamSetting() {
        return examSetting;
    }

    public void setExamSetting(ExamSetting examSetting) {
        this.examSetting = examSetting;
    }

    public List<ExamQuestion> getExamQuestions() {
        return examQuestions;
    }

    public void setExamQuestions(List<ExamQuestion> examQuestions) {
        this.examQuestions = examQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exam)) return false;
        if (!super.equals(o)) return false;

        Exam exam = (Exam) o;

        return uID.equals(exam.uID);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + uID.hashCode();
        return result;
    }
}
