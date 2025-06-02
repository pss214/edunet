package fiveguys.edunet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fiveguys.edunet.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}
