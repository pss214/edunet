package fiveguys.edunet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fiveguys.edunet.domain.Subject;
import fiveguys.edunet.domain.Teacher;
import fiveguys.edunet.form.CreateSubject;
import fiveguys.edunet.repository.SubjectRepository;
import fiveguys.edunet.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    public void saveSubject(CreateSubject form, String username) {
        if(teacherRepository.existsByUsername(username)){
            Teacher teacher = teacherRepository.findByUsername(username).get();
            subjectRepository.save(Subject.builder()
                .teacher(teacher)
                .subjectname(form.getSubjectname())
                .attend(form.getAttend())
                .startDay(form.getStartDay())
                .endDay(form.getEndDay())
                .price(form.getPrice())
                .deadDay(form.getDeadDay())
                .startTime(form.getStartTime())
                .endTime(form.getEndTime())
                .thumbnail("")
                .poster("")
                .build());
        }else{
            throw new UsernameNotFoundException("강사를 찾을 수 없습니다.");
        }
    }

    public boolean isSubjectCodeDuplicate(String subjectname) {
       return subjectRepository.findBySubjectname(subjectname).isPresent();
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }

    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }
}
