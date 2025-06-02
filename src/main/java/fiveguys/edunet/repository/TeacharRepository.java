package fiveguys.edunet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fiveguys.edunet.domain.Teacher;

@Repository
public interface TeacharRepository extends JpaRepository<Teacher, Long> {

}
