package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "difficultysettings")
@AttributeOverride(name = "id", column = @Column(name = "difficulty_settings_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class DifficultySetting extends BaseEntity {

    @Column(name = "percentage")
    @NotNull(message = "*Please set the percentage for this question difficulty")
    private int percentage;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="difficulty_id",referencedColumnName="difficulty_id")
    private Difficulty difficulty;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="exam_settings_id",referencedColumnName="exam_settings_id")
    private ExamSetting examSetting;

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public ExamSetting getExamSetting() {
        return examSetting;
    }

    public void setExamSetting(ExamSetting examSetting) {
        this.examSetting = examSetting;
    }
}
