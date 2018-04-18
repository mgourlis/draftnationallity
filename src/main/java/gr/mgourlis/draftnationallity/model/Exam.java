package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "exam")
@AttributeOverride(name = "id", column = @Column(name = "exam_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Exam extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "uID" , unique = true)
    @NotNull
    private String uID;

    @Column(name = "is_checked")
    @NotNull
    private Boolean isChecked;

    @Column(name = "file_number")
    private String fileNumber;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="exam_settings_id",referencedColumnName="exam_settings_id")
    private ExamSetting examSetting;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy="exams")
    private List<Question> questions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUID() {
        return uID;
    }

    public void setUID(String UID) {
        this.uID = uID;
    }

    public Boolean getChecked() { return isChecked; }

    public void setChecked(Boolean checked) { isChecked = checked; }

    public ExamSetting getExamSetting() { return examSetting; }

    public void setExamSetting(ExamSetting examSetting) { this.examSetting = examSetting; }

    public List<Question> getQuestions() { return questions; }

    public void setQuestions(List<Question> questions) { this.questions = questions; }
}
