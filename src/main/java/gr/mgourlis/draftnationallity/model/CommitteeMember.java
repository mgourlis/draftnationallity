package gr.mgourlis.draftnationallity.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "committee_members")
@AttributeOverride(name = "id", column = @Column(name = "committee_member_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class CommitteeMember extends BaseEntity {

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @Column(name = "committee_role")
    @NotEmpty
    private String committeeRole;

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

    public String getCommitteeRole() {
        return committeeRole;
    }

    public void setCommitteeRole(String committeeRole) {
        this.committeeRole = committeeRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommitteeMember)) return false;
        if (!super.equals(o)) return false;

        CommitteeMember that = (CommitteeMember) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getLastName() != null ? getLastName().equals(that.getLastName()) : that.getLastName() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        return result;
    }
}
