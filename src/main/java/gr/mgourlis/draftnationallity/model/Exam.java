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

    @Column(name = "UID")
    @NotNull
    private String UID;

    @Column(name = "settingname")
    @NotNull
    private String settingname;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="exam_settings_id",referencedColumnName="exam_settings_id")
    private ExamSettings examSettings;

    @ManyToMany(mappedBy="exams")
    private List<Question> questions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getSettingname() {
        return settingname;
    }

    public void setSettingname(String settingname) {
        this.settingname = settingname;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
