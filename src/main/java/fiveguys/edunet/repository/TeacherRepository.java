package fiveguys.edunet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fiveguys.edunet.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByUsername(String username);

    Optional<Teacher> findByUsername(String username);

    Optional<Teacher> findFirstByEmailOrNumber(String email, String number);
}
