package by.yachnikzakhar.courselog.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAuthority('STUDENT')")
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/main_page")
    public String showMainPage(Model model){
        return "student_main_page";
    }
}
