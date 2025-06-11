package fiveguys.edunet.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fiveguys.edunet.domain.Student;
import fiveguys.edunet.domain.Subject;
import fiveguys.edunet.domain.Teacher;
import fiveguys.edunet.form.CreateSubject;
import fiveguys.edunet.repository.StudentRepository;
import fiveguys.edunet.repository.SubjectRepository;
import fiveguys.edunet.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    public void saveSubject(CreateSubject form, String username) throws IOException {
        // String path = "/Users/pss/Desktop/edunet/src/main/resources/static/image/";
        String path = "C:\\새 폴더\\edunet\\src\\main\\resources\\static\\image\\";
        String thumbnailName = form.getThumbnail().getOriginalFilename();
        String posterName = form.getPoster().getOriginalFilename();
        if (thumbnailName == null || !thumbnailName.contains(".")) {
        }
        String extentionName = thumbnailName.substring(thumbnailName.lastIndexOf(".") + 1).toLowerCase();
        String extentionName2 = posterName.substring(posterName.lastIndexOf(".") + 1).toLowerCase();
        form.getThumbnail().transferTo(new File(path + form.getSubjectname() + "_thumbnail." + extentionName));
        form.getPoster().transferTo(new File(path + form.getSubjectname() + "_poster." + extentionName2));
        if (teacherRepository.existsByUsername(username)) {
            Teacher teacher = teacherRepository.findByUsername(username).get();
            Subject subject = Subject.builder()
                    .teacher(teacher)
                    .detail(form.getDetail())
                    .subjectname(form.getSubjectname())
                    .attend(form.getAttend())
                    .startDay(form.getStartDay())
                    .endDay(form.getEndDay())
                    .price(form.getPrice())
                    .deadDay(form.getDeadDay())
                    .startTime(form.getStartTime())
                    .endTime(form.getEndTime())
                    .thumbnail("/image/" + form.getSubjectname() + "_thumbnail." + extentionName)
                    .poster("/image/" + form.getSubjectname() + "_poster." + extentionName2)
                    .theme(form.getTheme())
                    .build();
            subjectRepository.save(subject);
        } else {
            throw new UsernameNotFoundException("강사를 찾을 수 없습니다.");
        }
    }

    public boolean isSubjectCodeDuplicate(String subjectname) {
        return subjectRepository.findBySubjectname(subjectname).isPresent();
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    // public Optional<Subject> findById(Long id) {
    //     return subjectRepository.findById(id);
    // }

    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }

    public Subject findById(Long id) {
        return subjectRepository.findById(id).get();
    }
    public void register(Long subjectId, String userId) {
        Student student = studentRepository.findByUsername(userId).get();
        Subject subject = subjectRepository.findById(subjectId).get();
        student.setSubject(subject);
        studentRepository.save(student);
    }
    public Subject myClass(String userId) throws Exception {
        Student student = studentRepository.findByUsername(userId).get();
        if(student.getSubject()==null) throw new NotFoundException();
        return student.getSubject();
    }
    @Transactional
    public Subject teacherClass(String teacherId) throws Exception{
        Teacher teacher = teacherRepository.findByUsername(teacherId).get();
        Subject subject = subjectRepository.findByTeacher(teacher);
        subject.getStudents().isEmpty();
        return subject;
    }
}
