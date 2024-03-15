package by.yachnikzakhar.courselog.controller;

import by.yachnikzakhar.courselog.dto.CourseDto;
import by.yachnikzakhar.courselog.mapper.CourseMapper;
import by.yachnikzakhar.courselog.model.Course;
import by.yachnikzakhar.courselog.model.User;
import by.yachnikzakhar.courselog.service.CourseService;
import by.yachnikzakhar.courselog.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@PreAuthorize("hasAuthority('EDUCATOR')")
@RequestMapping("/educator")
public class EducatorController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/main_page")
    public String showMainPage(){
        return "educator_main_page";
    }

    @GetMapping("/courses")
    public String showCourses(
            @AuthenticationPrincipal User user,
            Model model
    ){
        List<CourseDto> courses = CourseMapper.getInstance().coursesToCourseDtos(user.getCourses());
        model.addAttribute("courses", courses);
        return "educator_courses";
    }

    @GetMapping("/courses/{id}")
    public String showCourse(
        @PathVariable("id") Long id,
        Model model
    ){
        try {
            Course course = courseService.getCourseById(id);
            model.addAttribute("course", course);
            model.addAttribute("usernames", courseService.getUsernames(course));
            return "educator_course";
        } catch (ServiceException e) {
            return "redirect:/educator/courses?error=course_not_found";
        }
    }

}
