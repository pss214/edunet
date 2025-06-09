package fiveguys.edunet.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fiveguys.edunet.form.CreateForm;
import fiveguys.edunet.form.LoginForm;
import fiveguys.edunet.service.StudentService;
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
    @GetMapping("")
    public String getIndex(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if ( session == null) {
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
    public String postSignup(Model model,@ModelAttribute("signup")CreateForm form, RedirectAttributes redirectAttributes){
        if(form.getType().equals("student")){
            try {
                studentService.signup(form);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error","회원가입 실패 : "+e.getMessage());
                return "redirect:/signup";
            }
        }else{
            try {
                teacherService.signup(form);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error","회원가입 실패 : "+e.getMessage());
                return "redirect:/signup";
            }
        }
        return "redirect:/login";
    }
    @GetMapping("/main")
    public String getMain(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("현재 사용자: " + auth.getName());  // anonymousUser? 또는 실제 유저?
        return "mainPage";
    }
    @GetMapping("/subject")
    public String getSubject(HttpServletRequest request,Model model) {
        return "subjectPage";
    }
    @GetMapping("/student")
    public String getstudent(HttpServletRequest request,Model model) {
        return "studentdetail";
    }
    @GetMapping("/teacher")
    public String getteacher(HttpServletRequest request,Model model) {
        return "teacherdetail";
    }
}
