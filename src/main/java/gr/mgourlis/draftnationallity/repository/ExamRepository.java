package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ExamRepository")
public interface ExamRepository extends JpaRepository<Exam,Long> {
    public Exam findExamByIdAndDeleted(long id, boolean deleted);

    public Exam findExamByIdAndCreatedByAndDeleted(long id, String email, boolean deleted);

    public Exam findExamByUIDAndDeleted(String uID, boolean deleted);

    public Exam findExamByLocalFileNumberAndDeleted(String localFileNumber, boolean deleted);

    public Exam findExamByFileNumberAndDeleted(String fileNumber, boolean deleted);

    public List<Exam> findExamsByDeleted(boolean deleted);

    public Page<Exam> findExamsByDeleted(boolean deleted, Pageable pageable);

    public List<Exam> findExamsByCreatedByAndDeletedOrderByCreatedAtDesc(String createdBy, boolean deleted);

    public Page<Exam> findExamsByCreatedByAndDeletedOrderByCreatedAtDesc(String createdBy, boolean deleted, Pageable pageable);

    public List<Exam> findExamsByLocalFileNumberContainingAndCreatedByAndDeletedOrderByCreatedAtDesc(String localFileNumber, String email, boolean deleted);

    public Page<Exam> findExamsByLocalFileNumberContainingAndCreatedByAndDeletedOrderByCreatedAtDesc(String localFileNumber, String email, boolean deleted, Pageable pageable);

    public List<Exam> findExamsByStatusAndCreatedByAndDeletedOrderByCreatedAtDesc(ExamStatus status, String email, boolean deleted);

    public Page<Exam> findExamsByStatusAndCreatedByAndDeletedOrderByCreatedAtDesc(ExamStatus status, String email, boolean deleted, Pageable pageable);

    public List<Exam> findExamsByLocalFileNumberContainingAndStatusAndCreatedByAndDeletedOrderByCreatedAtDesc(String localFileNumber, ExamStatus status, String email, boolean deleted);

    public Page<Exam> findExamsByLocalFileNumberContainingAndStatusAndCreatedByAndDeletedOrderByCreatedAtDesc(String localFileNumber, ExamStatus status, String email, boolean deleted, Pageable pageable);

    public List<Exam> findExamsByStatusAndDeleted(ExamStatus status, boolean deleted);

    public Page<Exam> findExamsByStatusAndDeleted(ExamStatus status, boolean deleted, Pageable pageable);

    @Query(value = "select e from Exam e where (e.localFileNumber like %?1% or e.fileNumber like %?2% or e.uID like %?3%) and (e.status = ?4 or e.status = ?5) and e.deleted = ?6")
    public List<Exam> findExamsByLocalFileNumberContainingOrFileNumberContainingOrUIDContainingAndStatusAndDeleted(String localFileNumber, String FileNumber, String uID, ExamStatus status1, ExamStatus status2, boolean deleted);

    @Query(value = "select e from Exam e where (e.localFileNumber like %?1% or e.fileNumber like %?2% or e.uID like %?3%) and (e.status = ?4 or e.status = ?5) and e.deleted = ?6",
            countQuery = "select e from Exam e where (e.localFileNumber like %?1% or e.fileNumber like %?2% or e.uID like %?3%) and (e.status = ?4 or e.status = ?5) and e.deleted = ?6")
    public Page<Exam> findExamsByLocalFileNumberContainingOrFileNumberContainingOrUIDContainingAndStatusAndDeleted(String localFileNumber, String FileNumber, String uID, ExamStatus status1, ExamStatus status2, boolean deleted, Pageable pageable);
}
