package fiveguys.edunet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
public class StudentService implements UserDetailsService {
    private final StudentRepository sr;
    private final PasswordEncoder encoder;

    public void signup(CreateForm studentCreateForm) {
        if (sr.existsByUsername(studentCreateForm.getUsername())) {
            throw new UsernameNotFoundException("아이디가 중복입니다 다시 입력해주세요!");
        }
        if (!studentCreateForm.getPassword().equals(studentCreateForm.getPasswordck())) {
            throw new IllegalArgumentException("비밀번호가 다름니다 다시 입력해주세요!");
        }
        sr.save(Student.builder()
                .username(studentCreateForm.getUsername())
                .password(encoder.encode(studentCreateForm.getPassword()))
                .email(studentCreateForm.getEmail())
                .phone(studentCreateForm.getPhone())
                .name(studentCreateForm.getName())
                .subject(null)
                .build());
    }
    public void idfind(CreateForm studentCreateForm) {
        sr.save(Student.builder()
        .email(studentCreateForm.getEmail())
        .build()
        );
    }
    public void password(CreateForm studentCreateForm) {
        sr.save(Student.builder()
        .username(studentCreateForm.getUsername())
        .email(studentCreateForm.getEmail())
        .build()
        );
    }
    public boolean signin(LoginForm form) {
        if (sr.existsByUsername(form.getUsername())) {
            Student student = sr.findByUsername(form.getUsername()).get();
            if (encoder.matches(form.getPassword(), student.getPassword()))
                return true;
        }
        throw new UsernameNotFoundException("아이디나 비밀번호가 일치하지 않습니다 다시 입력해주세요");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> userOptional = sr.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("아이디나 비밀번호가 일치하지 않습니다 다시 입력해주세요");
        }

        Student foundUser = userOptional.get();
        return new User(
                foundUser.getUsername(),
                foundUser.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
