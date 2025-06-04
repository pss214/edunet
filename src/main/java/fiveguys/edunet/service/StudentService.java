package fiveguys.edunet.service;

import org.springframework.stereotype.Service;

import fiveguys.edunet.domain.Student;
import fiveguys.edunet.form.StudentCreateForm;
import fiveguys.edunet.form.StudentLoginForm;
import fiveguys.edunet.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository sr;

    public void create(StudentCreateForm form){
        if (sr.existsByUsername(form.getUsername())) {
            return;//존재하는 학생아이디
        }
        if(form.getPassword().equals(form.getPasswordck())){
            return;//비밀번호와 비밀번호확인이 다름
        }
        sr.save(Student.builder()
            .username(form.getUsername())
            .password(form.getPassword())//암호화로 바꿀 예정
            .studentname(form.getStudentName())
            .email(form.getEmail())
            .phonenumber(form.getPhonenumber())
            .build());     
        return;//완료되었을 때   
    }
    public void login(StudentLoginForm form){
        Student student;
        if(sr.existsByUsername(form.getUsername())){
            student = sr.findByUsername(form.getUsername()).get();
        }else{
            return; //아이디 정보가 없을 때
        }
        if(student.getPassword().equals(form.getPassword())){
            return;//비밀번호가 맞을 때
        }else{
            return;//비밀번호가 틀릴 떄
        }
    }
    public void findPassword(){

    }
    public void findUsername(){
        
    }
}
