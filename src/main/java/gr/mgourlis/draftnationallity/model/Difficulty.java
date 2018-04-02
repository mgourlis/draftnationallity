package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "difficulty")
@AttributeOverride(name = "id", column = @Column(name = "difficulty_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Difficulty extends BaseEntity {

    @Column(name = "level", unique=true)
    @NotNull(message = "*Please provide a level for the difficulty")
    private String level;

    @Column(name = "levelnum", unique=true)
    @NotNull(message = "*Please provide a level number for the difficulty")
    private int level_number;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getLevel_number() {
        return level_number;
    }

    public void setLevel_number(int level_number) {
        this.level_number = level_number;
    }
}
