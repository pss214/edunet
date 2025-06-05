package fiveguys.edunet.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fiveguys.edunet.domain.CustomUserDetails;
import fiveguys.edunet.domain.Student;
import fiveguys.edunet.domain.Teacher;
import fiveguys.edunet.repository.StudentRepository;
import fiveguys.edunet.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomDetailService implements UserDetailsService{
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> userOptional = studentRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다: " + username);
        }

        Student foundUser = userOptional.get();
        return new CustomUserDetails(
                foundUser.getEmail(),
                foundUser.getPassword(),
                "USER"
        );
    }
    public UserDetails loadTeacherByUsername(String username) throws UsernameNotFoundException {
        Optional<Teacher> userOptional = teacherRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다: " + username);
        }

        Teacher foundUser = userOptional.get();
        return new CustomUserDetails(
                foundUser.getEmail(),
                foundUser.getPassword(),
                "ADMIN"
        );
    }

}
