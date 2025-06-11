package fiveguys.edunet.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fiveguys.edunet.domain.Student;

import fiveguys.edunet.form.CreateForm;
import fiveguys.edunet.form.LoginForm;
import fiveguys.edunet.form.PasswordFindForm;
import fiveguys.edunet.form.PasswordNewForm;
import fiveguys.edunet.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService implements UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final StudentRepository sr;
    private final PasswordEncoder encoder;

    public void signup(CreateForm studentCreateForm) {
        String regex = "^01[016789]-?\\d{3,4}-?\\d{4}$";
        if (sr.existsByUsername(studentCreateForm.getUsername())) {
            throw new UsernameNotFoundException("아이디가 중복입니다 다시 입력해주세요!");
        }
        if (!studentCreateForm.getPassword().equals(studentCreateForm.getPasswordck())) {
            throw new IllegalArgumentException("비밀번호가 다름니다 다시 입력해주세요!");
        }
        if (!Pattern.matches(regex, studentCreateForm.getPhone())) {
            throw new IllegalArgumentException("연락처 형식이 잘못되었습니다 다시 입력해주세요!");
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

        // 화면에는 결과 노출 X (정보유출 방지)
        return "회원님의 아이디:" + s.getUsername();
    }

    public String findPassword(PasswordFindForm form) {
        if (sr.existsByUsername(form.getUsername())) {
            Student student = sr.findByUsername(form.getUsername()).get();
            if (student.getEmail().equals(form.getEmail())) {
                return student.getUsername();
            }
        }
        throw new UsernameNotFoundException("회원을 찾을 수가 없습니다");
    }

    @Transactional
    public void newPassword(PasswordNewForm form, String username) {
        Student student = sr.findByUsername(username).get();
        if (form.getPassword().equals(form.getPasswordck())) {
            student.setPassword(passwordEncoder.encode(form.getPassword()));
            sr.save(student);
            return;
        }
        throw new MatchException("비밀번호가 맞지 않습니다 다시 입력하세요", null);
    }
}
