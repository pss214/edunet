package fiveguys.edunet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("")
    public String getIndex(){
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String getLogin() {
        return "loginPage";
    }
    @GetMapping("/main")
    public String getMain() {
        return "mainPage";
    }
}
