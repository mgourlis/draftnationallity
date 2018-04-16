package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ExamRepository")
public interface ExamRepository extends JpaRepository<Exam,Long> {
    public Exam findFirstByCreatedByAndDeletedOrderByCreatedAtDesc(String createdBy, Boolean deleted);
    public Exam findExamsByCreatedByAndDeletedOrderByCreatedAtDesc(String createdBy, Boolean deleted);
    public Exam findExamByUIDAndDeleted(String UID, Boolean deleted);
    public List<Exam> findAllByDeleted(Boolean deleted);
}
