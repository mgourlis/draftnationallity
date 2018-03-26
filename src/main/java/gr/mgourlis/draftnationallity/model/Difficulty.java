package gr.mgourlis.draftnationallity.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "difficulty")
public class Difficulty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "difficulty_id")
    private int id;

    @Column(name = "level", unique=true)
    @NotEmpty(message = "*Please provide a level for the difficulty")
    private String level;

    @Column(name = "levelnum", unique=true)
    @NotEmpty(message = "*Please provide a level number for the difficulty")
    private int level_number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
