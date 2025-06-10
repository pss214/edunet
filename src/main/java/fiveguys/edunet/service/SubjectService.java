package fiveguys.edunet.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
    private final ResourceLoader resourceLoader;

    public void saveSubject(CreateSubject form, String username) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/image/");
        String thumbnailName = form.getThumbnail().getOriginalFilename();
        String posterName = form.getPoster().getOriginalFilename();
        if (thumbnailName == null || !thumbnailName.contains(".")) {
        }
        String extentionName = thumbnailName.substring(thumbnailName.lastIndexOf(".") + 1).toLowerCase();
        String extentionName2 = posterName.substring(posterName.lastIndexOf(".") + 1).toLowerCase();
        form.getThumbnail().transferTo(new File(resource + form.getSubjectname() + "_thumbnail" + extentionName));
        form.getPoster().transferTo(new File(resource + form.getSubjectname() + "_poster" + extentionName));
        if (teacherRepository.existsByUsername(username)) {
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
                    .thumbnail("/static/image/" + form.getSubjectname() + "_thumbnail" + extentionName)
                    .poster("/static/image/" + form.getSubjectname() + "_thumbnail" + extentionName2)
                    .build());
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

    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }

    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }
}
