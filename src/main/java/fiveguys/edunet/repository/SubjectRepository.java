package fiveguys.edunet.repository;

import fiveguys.edunet.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    boolean existsBySubjectCode(String subjectCode);
}
