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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fiveguys.edunet.filter.SessionConst;
import fiveguys.edunet.form.LoginForm;
import fiveguys.edunet.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    public static final String LOGIN_MEMBER = "loginMember";
    private final TeacherService teacherService;
    @PostMapping("/login")
    public String login(@ModelAttribute("login")LoginForm form,Model model,
    RedirectAttributes redirectAttributes , HttpServletRequest request){
        try {
            teacherService.signin(form);
            UserDetails userDetails = teacherService.loadUserByUsername(form.getUsername());
            Authentication auth = new UsernamePasswordAuthenticationToken(
            userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, userDetails);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            return "redirect:/main";
        } catch (UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error","로그인 실패 : "+e.getMessage());
            return "redirect:/login";
        }
    }
}
