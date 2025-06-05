package fiveguys.edunet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fiveguys.edunet.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {
    private final StudentRepository sr;
}
