package gr.mgourlis.draftnationallity.dto;

import gr.mgourlis.draftnationallity.model.CommitteeMember;
import gr.mgourlis.draftnationallity.model.CommitteeRole;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CommitteeMemberDTO {

    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String lastName;

    @NotNull
    private CommitteeRole committeeRole;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CommitteeRole getCommitteeRole() {
        return committeeRole;
    }

    public void setCommitteeRole(CommitteeRole committeeRole) {
        this.committeeRole = committeeRole;
    }

    public void init(CommitteeMember committeeMember){
        this.setId(committeeMember.getId());
        this.setName(committeeMember.getName());
        this.setLastName(committeeMember.getLastName());
        this.setCommitteeRole(committeeMember.getCommitteeRole());
    }
}
