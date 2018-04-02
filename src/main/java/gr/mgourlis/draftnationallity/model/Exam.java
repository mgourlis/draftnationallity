package gr.mgourlis.draftnationallity.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "exam")
@AttributeOverride(name = "id", column = @Column(name = "exam_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Exam extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "settingname")
    private String settingname;

    @ManyToMany(mappedBy="exams")
    private List<Question> questions;


}
