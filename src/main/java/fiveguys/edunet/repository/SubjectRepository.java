package fiveguys.edunet.repository;

import fiveguys.edunet.domain.Subject;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    
        // subjectname 기준 중복 검사용 메서드
        Optional<Subject> findBySubjectname(String subjectname);
}
