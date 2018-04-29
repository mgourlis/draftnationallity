package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ExamRepository")
public interface ExamRepository extends JpaRepository<Exam,Long> {
    public Exam findExamByIdAndDeleted(long id, boolean deleted);
    public Exam findExamByUIDAndDeleted(String uID, boolean deleted);
    public Exam findExamByLocalFileNumberAndDeleted(String localFileNumber, boolean deleted);
    public Exam findExamByFileNumberAndDeleted(String fileNumber, boolean deleted);
    public List<Exam> findExamsByDeleted(boolean deleted);
    public Page<Exam> findExamsByDeleted(boolean deleted, Pageable pageable);
    public List<Exam> findExamsByCreatedByAndDeletedOrderByCreatedAtDesc(String createdBy, boolean deleted);
    public Page<Exam> findExamsByCreatedByAndDeletedOrderByCreatedAtDesc(String createdBy, boolean deleted, Pageable pageable);
}
