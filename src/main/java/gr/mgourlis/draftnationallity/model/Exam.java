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

    @Column(name = "answered_date")
    @Temporal(TemporalType.DATE)
    private Date answeredDate;

    @Column(name = "rated_date")
    @Temporal(TemporalType.DATE)
    private Date ratedDate;

    @Column(name = "language_exemption")
    private boolean languageExemption;

    @Column(name = "language_exemption_notes", length = 512)
    private String languageExemptionNotes;

    @Column(name = "deaf")
    private boolean deaf;

    @Column(name = "deaf_notes", length = 512)
    private String deafNotes;

    @Column(name = "validated_date")
    @Temporal(TemporalType.DATE)
    private Date validatedDate;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private ExamStatus status;

    @Lob
    @Column(name = "exam_general_notes", length = 1024)
    private String examGeneralNotes;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "exam_setting_id", referencedColumnName = "exam_setting_id", nullable = false)
    private ExamSetting examSetting;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_id", referencedColumnName = "exam_id", nullable = false)
    private List<ExamQuestion> examQuestions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_id", referencedColumnName = "exam_id", nullable = false)
    private List<ExamRating> examRatings;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_id", referencedColumnName = "exam_id", nullable = false)
    private List<CommitteeMember> committeeMembers;

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

    public Date getAnsweredDate() {
        return answeredDate;
    }

    public void setAnsweredDate(Date answeredDate) {
        this.answeredDate = answeredDate;
    }

    public Date getRatedDate() {
        return ratedDate;
    }

    public void setRatedDate(Date ratedDate) {
        this.ratedDate = ratedDate;
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

    public Date getValidatedDate() {
        return validatedDate;
    }

    public void setValidatedDate(Date validatedDate) {
        this.validatedDate = validatedDate;
    }

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
    }

    public String getExamGeneralNotes() {
        return examGeneralNotes;
    }

    public void setExamGeneralNotes(String examGeneralNotes) {
        this.examGeneralNotes = examGeneralNotes;
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

    public List<ExamRating> getExamRatings() {
        return examRatings;
    }

    public void setExamRatings(List<ExamRating> examRatings) {
        this.examRatings = examRatings;
    }

    public List<CommitteeMember> getCommitteeMembers() {
        return committeeMembers;
    }

    public void setCommitteeMembers(List<CommitteeMember> committeeMembers) {
        this.committeeMembers = committeeMembers;
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
