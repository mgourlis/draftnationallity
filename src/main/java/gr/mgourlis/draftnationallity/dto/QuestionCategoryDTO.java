package gr.mgourlis.draftnationallity.dto;

import gr.mgourlis.draftnationallity.model.QuestionCategory;

import javax.validation.constraints.NotEmpty;

public class QuestionCategoryDTO {

    private long id;

    @NotEmpty
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void init(QuestionCategory questionCategory){
        this.setId(questionCategory.getId());
        this.setName(questionCategory.getName());
    }
}
