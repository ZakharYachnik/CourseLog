package by.yachnikzakhar.courselog.controller;


import by.yachnikzakhar.courselog.dto.CourseDto;
import by.yachnikzakhar.courselog.mapper.CourseMapper;
import by.yachnikzakhar.courselog.model.Course;
import by.yachnikzakhar.courselog.model.Lesson;
import by.yachnikzakhar.courselog.model.User;
import by.yachnikzakhar.courselog.service.CourseService;
import by.yachnikzakhar.courselog.service.EducatorService;
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
@PreAuthorize("hasAuthority('STUDENT')")
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private EducatorService educatorService;

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

    @GetMapping("/courses/{id}/lessons")
    public String showLessons(
            @PathVariable("id") Long id,
            Model model
    ) {
        try {
            Course course = courseService.getCourseById(id);
            model.addAttribute("courseId", id);
            model.addAttribute("lessons", course.getLessons());
            return "student_lessons";
        } catch (ServiceException e) {
            return "redirect:/student/courses?error=course_not_found";
        }
    }

    @GetMapping("/courses/{courseId}/lessons/{lessonId}")
    public String showLesson(
            @PathVariable("courseId") Long courseId,
            @PathVariable("lessonId") Long lessonId,
            Model model
    ) {
        try {
            Lesson lesson = educatorService.getLessonById(lessonId);
            model.addAttribute("courseId", courseId);
            model.addAttribute("lesson", lesson);
            return "student_lesson";
        } catch (ServiceException e) {
            return "redirect:/educator/courses/" + courseId + "/lessons?error=lesson_not_found";
        }
    }
}
