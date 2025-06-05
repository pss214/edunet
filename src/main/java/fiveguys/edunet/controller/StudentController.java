package fiveguys.edunet.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fiveguys.edunet.domain.Student;
import fiveguys.edunet.form.CreateForm;
import fiveguys.edunet.repository.StudentRepository;
import fiveguys.edunet.service.CustomDetailService;
import fiveguys.edunet.service.StudentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor

public class StudentController {
  private final StudentService studentService;
  private final CustomDetailService customDetailService;
    
}
