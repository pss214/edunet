package fiveguys.edunet.service;

import org.springframework.stereotype.Service;

import fiveguys.edunet.domain.Teacher;
import fiveguys.edunet.form.TeacherCreateForm;
import fiveguys.edunet.form.TeacherLoginForm;
import fiveguys.edunet.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository tr;

    public void create(TeacherCreateForm form){
        if (tr.existsByUsername(form.getUsername())) {
            return;//존재하는 학생아이디
        }
        if(form.getPassword().equals(form.getPasswordck())){
            return;//비밀번호와 비밀번호확인이 다름
        }
        tr.save(Teacher.builder()
            .username(form.getUsername())
            .password(form.getPassword())//암호화로 바꿀 예정
            .name(form.getName())
            .email(form.getEmail())
            .number(form.getPhonenumber())
            .build());     
        return;//완료되었을 때   
    }
    public void login(TeacherLoginForm form){
        Teacher teacher;
        if(tr.existsByUsername(form.getUsername())){
            teacher = tr.findByUsername(form.getUsername()).get();
        }else{
            return; //아이디 정보가 없을 때
        }
        if(teacher.getPassword().equals(form.getPassword())){
            return;//비밀번호가 맞을 때
        }else{
            return;//비밀번호가 틀릴 떄
        }
    }

}
