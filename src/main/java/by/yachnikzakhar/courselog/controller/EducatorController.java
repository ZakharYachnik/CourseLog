package by.yachnikzakhar.courselog.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAuthority('EDUCATOR')")
@RequestMapping("/educator")
public class EducatorController {

    @GetMapping("/main_page")
    public String showMainPage(){
        return "educator_main_page";
    }
}
