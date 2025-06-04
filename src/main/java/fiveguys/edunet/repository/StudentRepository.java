package fiveguys.edunet.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fiveguys.edunet.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

    Boolean existsByUsername(String username);
    Optional<Student> findByUsername(String username);

}
