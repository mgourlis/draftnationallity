package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

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

    @Column(name = "is_validated")
    @NotNull
    private Boolean isValidated;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "exam_setting_id", referencedColumnName = "exam_setting_id")
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

    public Boolean getValidated() {
        return isValidated;
    }

    public void setValidated(Boolean validated) {
        isValidated = validated;
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
