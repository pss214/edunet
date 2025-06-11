package fiveguys.edunet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fiveguys.edunet.domain.Subject;
import fiveguys.edunet.domain.Teacher;


public interface SubjectRepository extends JpaRepository<Subject, Long> {
    
        // subjectname 기준 중복 검사용 메서드
        Optional<Subject> findBySubjectname(String subjectname);
        Subject findByTeacher(Teacher teacher);
}
