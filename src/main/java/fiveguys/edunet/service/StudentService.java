package fiveguys.edunet.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fiveguys.edunet.domain.Student;
import fiveguys.edunet.form.CreateForm;
import fiveguys.edunet.form.LoginForm;
import fiveguys.edunet.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository sr;
    private final PasswordEncoder encoder;
    public void signup(CreateForm studentCreateForm) {
        if(sr.existsByUsername(studentCreateForm.getUsername())) {
            throw new UsernameNotFoundException("ID 중복");
        }
        if (!studentCreateForm.getPassword().equals(studentCreateForm.getPasswordck())) {
            throw new IllegalArgumentException("PW 다름");
        }
        sr.save(Student.builder()
        .username(studentCreateForm.getUsername())
        .password(encoder.encode(studentCreateForm.getPassword()))
        .email(studentCreateForm.getEmail())
        .phone(studentCreateForm.getPhone())
        .name(studentCreateForm.getName())
        .build()
        );
    }
    public boolean signin(LoginForm form) {
        if(sr.existsByUsername(form.getUsername())) {
            Student student = sr.findByUsername(form.getUsername()).get();
            if(encoder.matches(form.getPassword(), student.getPassword())) return true;
        }
        return false;
    }
}
