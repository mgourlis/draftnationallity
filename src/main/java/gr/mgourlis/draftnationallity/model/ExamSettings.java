package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "examsettings")
@AttributeOverride(name = "id", column = @Column(name = "exam_settings_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class ExamSettings extends BaseEntity {

    @Column(name = "name", unique=true)
    @NotNull(message = "*Please provide a name for the exam settings")
    private String name;

    @Column(name = "number_of_questions")
    @NotNull(message = "*Please how many questions this exam settings has")
    private int numOfQuestions;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval=true, mappedBy = "examSetting")
    private List<DifficultySettings> difficultySettings;

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

    public List<DifficultySettings> getDifficultySettings() {
        return difficultySettings;
    }

    public void setDifficultySettings(List<DifficultySettings> difficultySettings) {
        this.difficultySettings = difficultySettings;
    }
}
