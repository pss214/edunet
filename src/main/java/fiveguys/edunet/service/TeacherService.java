package fiveguys.edunet.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fiveguys.edunet.domain.Teacher;
import fiveguys.edunet.form.CreateForm;
import fiveguys.edunet.form.LoginForm;
import fiveguys.edunet.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherService implements UserDetailsService{
    private final TeacherRepository tr;
    private final PasswordEncoder encoder;
    public void signup(CreateForm studentCreateForm) {
        String regex = "^01[016789]-?\\d{3,4}-?\\d{4}$";
        System.out.println("값 들어옴");
        if(tr.existsByUsername(studentCreateForm.getUsername())) {
            throw new UsernameNotFoundException("ID 중복");
        }
        if (!studentCreateForm.getPassword().equals(studentCreateForm.getPasswordck())) {
            throw new IllegalArgumentException("PW 다름");
        }
        if (!Pattern.matches(regex,studentCreateForm.getPhone())) {
            throw new IllegalArgumentException("연락처 형식이 잘못되었습니다 다시 입력해주세요!");
        }
        tr.save(Teacher.builder()
        .username(studentCreateForm.getUsername())
        .password(encoder.encode(studentCreateForm.getPassword()))
        .email(studentCreateForm.getEmail())
        .phone(studentCreateForm.getPhone())
        .name(studentCreateForm.getName())
        .build()
        );
    }
    public boolean signin(LoginForm form) {
        if(tr.existsByUsername(form.getUsername())) {
            Teacher teacher = tr.findByUsername(form.getUsername()).get();
            if(encoder.matches(form.getPassword(), teacher.getPassword())) return true;
        }
        throw new UsernameNotFoundException("아이디나 비밀번호가 일치하지 않습니다 다시 입력해주세요");
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Teacher> userOptional = tr.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("아이디나 비밀번호가 일치하지 않습니다 다시 입력해주세요");
        }

        Teacher foundUser = userOptional.get();
        return new User(
            foundUser.getUsername(),
            foundUser.getPassword(),
            // 관리자 권한 부여
            List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }
}