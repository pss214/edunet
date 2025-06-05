package fiveguys.edunet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fiveguys.edunet.form.StudentCreateForm;


@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("")
    public String getIndex(){
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String getLogin(Model model) {
        return "loginPage";
    }
    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("user", new StudentCreateForm());
        return "signupPage";
    }
    @GetMapping("/main")
    public String getMain(Model model) {
        return "mainPage";
    }
}
