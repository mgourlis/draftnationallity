package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "examsettings")
@AttributeOverride(name = "id", column = @Column(name = "exam_settings_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class ExamSetting extends BaseEntity {

    @Column(name = "name", unique=true)
    @NotNull(message = "*Please provide a name for the exam settings")
    private String name;

    @Column(name = "number_of_questions")
    @NotNull(message = "*Please how many questions this exam settings has")
    private int numOfQuestions;

    @Column(name = "enabled")
    @NotNull(message = "*Please set if this exam settings is enabled")
    private Boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval=true, mappedBy = "examSetting")
    private List<DifficultySetting> difficultySettings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<DifficultySetting> getDifficultySettings() {
        return difficultySettings;
    }

    public void setDifficultySettings(List<DifficultySetting> difficultySettings) {
        this.difficultySettings = difficultySettings;
    }
}
