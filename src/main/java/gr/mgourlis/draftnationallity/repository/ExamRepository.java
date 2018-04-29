package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ExamRepository")
public interface ExamRepository extends JpaRepository<Exam,Long> {
    public Exam findFirstByCreatedByAndDeletedOrderByCreatedAtDesc(String createdBy, Boolean deleted);
    public Exam findExamsByCreatedByAndDeletedOrderByCreatedAtDesc(String createdBy, Boolean deleted);
    public Exam findExamByUIDAndDeleted(String uID, Boolean deleted);
    public Exam findExamByLocalFileNumberAndDeleted(String localFileNumber, Boolean deleted);
    public Exam findExamByFileNumberAndDeleted(String fileNumber, Boolean deleted);
    public List<Exam> findExamsByDeleted(Boolean deleted);
    public Page<Exam> findExamsByDeleted(Boolean deleted, Pageable pageable);
}
