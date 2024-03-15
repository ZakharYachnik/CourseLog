package by.yachnikzakhar.courselog.controller;


import by.yachnikzakhar.courselog.dto.CourseDto;
import by.yachnikzakhar.courselog.mapper.CourseMapper;
import by.yachnikzakhar.courselog.model.User;
import by.yachnikzakhar.courselog.service.CourseService;
import by.yachnikzakhar.courselog.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@PreAuthorize("hasAuthority('STUDENT')")
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/main_page")
    public String showMainPage(Model model) {
        return "student_main_page";
    }

    @GetMapping("/courses")
    public String showCourses(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        List<CourseDto> courses = CourseMapper.getInstance().coursesToCourseDtos(user.getCourses());
        model.addAttribute("courses", courses);
        return "student_courses";
    }


}
