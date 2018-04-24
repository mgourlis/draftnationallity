package gr.mgourlis.draftnationallity.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "difficulty")
@AttributeOverride(name = "id", column = @Column(name = "difficulty_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Difficulty extends BaseEntity {

    @Column(name = "level")
    @NotEmpty(message = "*Please provide a level for the difficulty")
    private String level;

    @Column(name = "levelnum")
    @Min(1)
    private int levelNumber;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }
}
