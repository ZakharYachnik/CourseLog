package by.yachnikzakhar.courselog.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Transactional
    @GetMapping("/")
    public String main() {
        return "redirect:/login";
    }

    @GetMapping("/redirectUserMainPage")
    public String redirectToUserMainPage(Authentication auth) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return "redirect:/admin/main_page";
        }
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("EDUCATOR"))) {
            return "redirect:/educator/main_page";
        }
        return "redirect:/student/main_page";
    }
}
