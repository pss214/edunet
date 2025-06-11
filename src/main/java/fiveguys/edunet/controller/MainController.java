package fiveguys.edunet.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fiveguys.edunet.domain.Subject;
import fiveguys.edunet.form.CreateForm;
import fiveguys.edunet.form.CreateSubject;
import fiveguys.edunet.form.Emailform;
import fiveguys.edunet.form.LoginForm;
import fiveguys.edunet.service.StudentService;
import fiveguys.edunet.service.SubjectService;
import fiveguys.edunet.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;

    @GetMapping("")
    public String getIndex(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        return "redirect:/main";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("login", new LoginForm());
        return "loginPage";
    }

    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("signup", new CreateForm());
        return "signupPage";
    }

    @PostMapping("/signup")
    public String postSignup(Model model, @ModelAttribute("signup") CreateForm form,
            RedirectAttributes redirectAttributes) {
        if (form.getType().equals("student")) {
            try {
                studentService.signup(form);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "회원가입 실패 : " + e.getMessage());
                return "redirect:/signup";
            }
        } else {
            try {
                teacherService.signup(form);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "회원가입 실패 : " + e.getMessage());
                return "redirect:/signup";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/idfind")
    public String getidfind(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("idfind", new Emailform());
        return "idfindPage";
    }

    @PostMapping("/idfind")
    public String postidfind(Model model, @ModelAttribute("idfind") Emailform form,
            RedirectAttributes redirectAttributes) {
        model.addAttribute("idfind", new Emailform());
        redirectAttributes.addFlashAttribute("username", studentService.findUsernameByEmail(form.getEmail()));
        return "redirect:/idfind";
    }

    @GetMapping("/password")
    public String getpassword(Model model) {
        model.addAttribute("signup", new CreateForm());
        return "passwordPage";
    }

    @GetMapping("/main")
    public String getMain(Model model) {
        List<Subject> subjects = subjectService.findAll();
        model.addAttribute("subjects", subjects);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("현재 사용자: " + auth.getName()); // anonymousUser? 또는 실제 유저?
        return "mainPage";
    }

    @GetMapping("/subject/{id}")
    public String getSubject(HttpServletRequest request, Model model, @PathVariable Long id) {
        Subject subject = subjectService.findById(id);
        model.addAttribute("subject", subject);
        model.addAttribute("teacher", subject.getTeacher());
        return "subjectPage";
    }

    @GetMapping("/student-detail")
    public String getstudent(HttpServletRequest request, Model model, @AuthenticationPrincipal User user) {
        try {
            model.addAttribute("subject", subjectService.myClass(user.getUsername()));
        } catch (Exception e) {
            model.addAttribute("error", "강좌가 없습니다.");
        }
        return "studentdetail";
    }

    @GetMapping("/create-subject")
    public String getcreateSubject(HttpServletRequest request, Model model) {
        model.addAttribute("object", new CreateSubject());
        return "createSubjectPage";
    }
    @GetMapping("/teacher-detail")
    public String getteacher(HttpServletRequest request, Model model, @AuthenticationPrincipal User user) {
        try {
            Subject subject = subjectService.teacherClass(user.getUsername());
            model.addAttribute("students", subject.getStudents());
            model.addAttribute("subject", subject);
        } catch (Exception e) {
            model.addAttribute("students", null);
            model.addAttribute("subject", null);
        }

        return "teacherdetail";
    }

}
