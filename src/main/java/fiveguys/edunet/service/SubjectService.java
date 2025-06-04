package fiveguys.edunet.service;

import fiveguys.edunet.domain.Subject;
import fiveguys.edunet.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    public boolean isSubjectCodeDuplicate(String subjectCode) {
        return subjectRepository.existsBySubjectCode(subjectCode);
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
