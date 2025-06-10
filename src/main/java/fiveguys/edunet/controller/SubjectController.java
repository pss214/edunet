package fiveguys.edunet.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import fiveguys.edunet.form.CreateSubject;
import fiveguys.edunet.service.SubjectService;
import lombok.RequiredArgsConstructor;



@Controller
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {

  private final SubjectService subjectService;
    @PostMapping("/create")
    public String postSubjecString(@AuthenticationPrincipal User user, @ModelAttribute CreateSubject form) {
        try {
            subjectService.saveSubject(form, user.getUsername());
        } catch (Exception e) {
        }
        
        return "redirect:/main";
    }
    @PostMapping("/upload")
    public String postImage(@RequestBody String entity) {
        
        return entity;
    }
    
}
