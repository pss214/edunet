package fiveguys.edunet.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fiveguys.edunet.domain.CustomUserDetails;
import fiveguys.edunet.form.CreateForm;
import fiveguys.edunet.form.LoginForm;
import fiveguys.edunet.service.CustomDetailService;
import fiveguys.edunet.service.StudentService;
import fiveguys.edunet.service.TeacherService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CustomDetailService customDetailService;
    @GetMapping("")
    public String getIndex(){
        return "redirect:/login";
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
                e.printStackTrace();
                return "redirect:/singup";
            }
        }else{
            try {
                teacherService.signup(form);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error","회원가입 실패 : "+e.getMessage());
                e.printStackTrace();
                return "redirect:/singup";
            }
        }
        return "redirect:/main";
    }
    @GetMapping("/main")
    public String getMain(Model model) {
        return "mainPage";
    }
    @PostMapping("/login")
    public String postSignin(@ModelAttribute("login")LoginForm form,Model model){
        if(form.getUser().equals("student")){
            System.out.println("student로 이동됨");
            if(studentService.signin(form)) {
                UserDetails userDetails = customDetailService.loadUserByUsername(form.getUsername());
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                return "redirect:/login";
            }
        }else{
            System.out.println("teacher로 이동됨");
            if(teacherService.signin(form)) {
                UserDetails userDetails = new CustomUserDetails( form.getUsername(),"","ADMIN");
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("강사 로그인 실패함");
                return "redirect:/login";
            }
        }
        return "redirect:/main";
    }
}
