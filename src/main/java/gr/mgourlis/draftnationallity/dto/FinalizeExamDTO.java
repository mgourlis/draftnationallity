package gr.mgourlis.draftnationallity.dto;

import gr.mgourlis.draftnationallity.model.CommitteeMember;
import gr.mgourlis.draftnationallity.model.Exam;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class FinalizeExamDTO {

    private long id;

    @NotEmpty
    private List<CommitteeMemberDTO> committeeMembersDTO;

    @Size(max = 1024)
    private String examGeneralNotes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<CommitteeMemberDTO> getCommitteeMembersDTO() {
        return committeeMembersDTO;
    }

    public void setCommitteeMembersDTO(List<CommitteeMemberDTO> committeeMembersDTO) {
        this.committeeMembersDTO = committeeMembersDTO;
    }

    public String getExamGeneralNotes() {
        return examGeneralNotes;
    }

    public void setExamGeneralNotes(String examGeneralNotes) {
        this.examGeneralNotes = examGeneralNotes;
    }

    public void init(Exam exam){
        this.setId(exam.getId());
        List<CommitteeMemberDTO> committeeMembersDTO = new ArrayList<>();
        for (CommitteeMember committeeMember : exam.getCommitteeMembers()) {
            CommitteeMemberDTO committeeMemberDTO = new CommitteeMemberDTO();
            committeeMemberDTO.init(committeeMember);
            committeeMembersDTO.add(committeeMemberDTO);
        }
        this.setCommitteeMembersDTO(committeeMembersDTO);
        this.setExamGeneralNotes(exam.getExamGeneralNotes());
    }
}
