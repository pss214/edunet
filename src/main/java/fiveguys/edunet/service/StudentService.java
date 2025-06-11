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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;

import fiveguys.edunet.domain.Student;

import fiveguys.edunet.domain.Subject;
import fiveguys.edunet.form.CreateForm;
import fiveguys.edunet.form.LoginForm;

import fiveguys.edunet.repository.StudentRepository;
import fiveguys.edunet.repository.SubjectRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService implements UserDetailsService {
    private final StudentRepository sr;
    private final PasswordEncoder encoder;

    private final JavaMailSender mailSender;

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

    @Transactional(readOnly = true)
    public String findUsernameByEmail(String email) {
        Student s = sr.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 회원이 없습니다"));

        mailSender.send(simpleMessage("[Edunet] 요청하신 아이디입니다", "회원님의 아이디: " + s.getUsername(), email));

        // 화면에는 결과 노출 X (정보유출 방지)
        return "입력하신 이메일로 아이디를 발송했습니다.";
    }

    private MimeMessage simpleMessage(String string, String string2, String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'simpleMessage'");
    }

}
