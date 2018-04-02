package gr.mgourlis.draftnationallity.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "difficultysettings")
@AttributeOverride(name = "id", column = @Column(name = "difficulty_settings_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class DifficultySettings extends BaseEntity {

    @Column(name = "percentage")
    @NotEmpty(message = "*Please set the percentage for this question difficulty")
    private int percentage;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="difficulty_id",referencedColumnName="difficulty_id")
    private Difficulty difficulty;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="difficulty_settings_id",referencedColumnName="difficulty_settings_id")
    private ExamSettings examSetting;

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

    public ExamSettings getExamSetting() {
        return examSetting;
    }

    public void setExamSetting(ExamSettings examSetting) {
        this.examSetting = examSetting;
    }
}
