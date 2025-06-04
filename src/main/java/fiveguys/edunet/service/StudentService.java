package fiveguys.edunet.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fiveguys.edunet.domain.Student;
import fiveguys.edunet.form.StudentCreateForm;
import fiveguys.edunet.form.StudentLoginForm;
import fiveguys.edunet.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder; // BCryptPasswordEncoder Bean 주입

    /**
     * 회원 가입
     */
    @Transactional
    public Student create(StudentCreateForm form) {
        // 1) 중복 아이디 검사
        if (studentRepository.existsByUsername(form.getUsername())) {
            throw new IllegalStateException("이미 사용 중인 아이디입니다.");
        }

        // 2) 비밀번호 확인 (✔ equals 결과를 부정해야 함)
        if (!form.getPassword().equals(form.getPasswordck())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 3) 엔티티 생성 & 저장
        Student student = Student.builder()
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword())) // 암호화
                .studentname(form.getStudentName())
                .email(form.getEmail())
                .phonenumber(form.getPhonenumber())
                .build();

        return studentRepository.save(student);
    }

    /**
     * 로그인
     */
    public Student login(StudentLoginForm form) {
        // 1) 아이디 존재 여부
        Student student = studentRepository.findByUsername(form.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        // 2) 비밀번호 일치 여부
        if (!passwordEncoder.matches(form.getPassword(), student.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        // 3) 로그인 성공 시 Student 반환(세션 저장 등은 컨트롤러/필터 계층에서 처리)
        return student;
    }

    /**
     * 아이디 찾기: 이메일 또는 휴대전화로 검색
     */
    public Optional<String> findUsername(String email, String phoneNumber) {
        return studentRepository
                .findFirstByEmailOrPhonenumber(email, phoneNumber)
                .map(Student::getUsername);
    }

    /**
     * 비밀번호 재설정(임시비밀번호 발급용)
     * 실제 서비스에서는 메일·SMS 전송 모듈과 연동해 임시 비밀번호를 발급하거나
     * 토큰 기반 재설정 링크를 보내는 방식이 일반적입니다.
     */

}
