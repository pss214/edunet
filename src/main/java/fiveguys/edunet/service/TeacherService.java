package fiveguys.edunet.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fiveguys.edunet.domain.Teacher;
import fiveguys.edunet.form.TeacherCreateForm;
import fiveguys.edunet.form.TeacherLoginForm;
import fiveguys.edunet.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherService {

    private final TeacherRepository teacherRepository;

    /*
     * ─────────────────*
     * 회원 가입(Create)
     * ─────────────────
     */
    @Transactional
    public Teacher create(TeacherCreateForm form) {

        // 1) 아이디 중복 검사
        if (teacherRepository.existsByUsername(form.getUsername())) {
            throw new IllegalStateException("이미 사용 중인 교사 아이디입니다.");
        }

        // 2) 비밀번호/비밀번호 확인 일치 여부
        if (!form.getPassword().equals(form.getPasswordck())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 다릅니다.");
        }
        tr.save(Teacher.builder()
            .username(form.getUsername())
            .password(form.getPassword())//암호화로 바꿀 예정
            .name(form.getName())
            .email(form.getEmail())
            .phone(form.getPhone())
            .build());     
        return;//완료되었을 때   
    }

    /*
     * ────────────*
     * 로그인
     * ────────────
     */
    public Teacher login(TeacherLoginForm form) {

        // 1) 아이디 존재 여부
        Teacher teacher = teacherRepository.findByUsername(form.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 교사 아이디입니다."));

        // // 2) 비밀번호 일치 여부
        // if (!passwordEncoder.matches(form.getPassword(), teacher.getPassword())) {
        //     throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        // }

        // 3) 성공 시 Teacher 반환(컨트롤러·필터에서 세션/토큰 처리)
        return teacher;
    }

    /*
     * ───────────────────────────────*
     * (선택) 아이디/비밀번호 찾기 예시
     * ───────────────────────────────
     */
    public Optional<String> findUsername(String email, String phoneNumber) {
        return teacherRepository
                .findFirstByEmailOrNumber(email, phoneNumber)
                .map(Teacher::getUsername);
    }
}