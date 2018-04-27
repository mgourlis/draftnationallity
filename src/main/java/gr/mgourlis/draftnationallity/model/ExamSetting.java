package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "examsettings")
@AttributeOverride(name = "id", column = @Column(name = "exam_setting_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class ExamSetting extends BaseEntity {

    @Column(name = "name")
    @NotEmpty(message = "*Please provide a name for the exam settings")
    private String name;

    @Column(name = "number_of_questions")
    @Min(1)
    private int numOfQuestions;

    @Column(name = "enabled")
    private Boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="exam_setting_id", referencedColumnName="exam_setting_id")
    @NotEmpty
    private List<DifficultySetting> difficultySettings;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "questioncategory_examsetting", joinColumns = @JoinColumn(name = "exam_settings_id"), inverseJoinColumns = @JoinColumn(name = "question_category_id"))
    @NotEmpty
    private Set<QuestionCategory> questionCategories;

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

    public Set<QuestionCategory> getQuestionCategories() {
        return questionCategories;
    }

    public void setQuestionCategories(Set<QuestionCategory> questionCategories) {
        this.questionCategories = questionCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamSetting)) return false;
        if (!super.equals(o)) return false;

        ExamSetting that = (ExamSetting) o;

        if (getNumOfQuestions() != that.getNumOfQuestions()) return false;
        if (!getName().equals(that.getName())) return false;
        return getEnabled().equals(that.getEnabled());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getNumOfQuestions();
        result = 31 * result + getEnabled().hashCode();
        return result;
    }
}
