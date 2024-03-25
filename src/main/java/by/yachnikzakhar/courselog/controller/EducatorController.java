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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@PreAuthorize("hasAuthority('EDUCATOR')")
@RequestMapping("/educator")
public class EducatorController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private EducatorService educatorService;

    @GetMapping("/main_page")
    public String showMainPage() {
        return "educator_main_page";
    }

    @GetMapping("/courses")
    public String showCourses(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        List<CourseDto> courses = CourseMapper.getInstance().coursesToCourseDtos(user.getCourses());
        model.addAttribute("courses", courses);
        return "educator_courses";
    }

    @GetMapping("/courses/{id}")
    public String showCourse(
            @PathVariable("id") Long id,
            Model model
    ) {
        try {
            Course course = courseService.getCourseById(id);
            model.addAttribute("course", course);
            model.addAttribute("usernames", courseService.getUsernames(course));
            return "educator_course";
        } catch (ServiceException e) {
            return "redirect:/educator/courses?error=course_not_found";
        }
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
            return "educator_lessons";
        } catch (ServiceException e) {
            return "redirect:/educator/courses?error=course_not_found";
        }
    }

    @GetMapping("/courses/{id}/add_lesson")
    public String addLesson(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("courseId", id);
        return "add_lesson";
    }

    @PostMapping("/courses/{id}/add_lesson")
    public String addLesson(
            @PathVariable("id") Long id,
            @ModelAttribute("lesson") Lesson lesson
    ) {
        try {
            lesson.setId(null);
            educatorService.addLesson(lesson, id);
            return "redirect:/educator/courses/" + id + "/lessons";
        } catch (ServiceException e) {
            return "redirect:/educator/courses/" + id + "/add_lesson?error=adding_lesson_error";
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
            return "educator_lesson";
        } catch (ServiceException e) {
            return "redirect:/educator/courses/" + courseId + "/lessons?error=lesson_not_found";
        }
    }

    @PostMapping("/courses/{courseId}/change_lesson")
    public String changeLesson(
            @PathVariable("courseId") Long courseId,
            @RequestParam("lessonId") Long lessonId,
            @RequestParam("lessonName") String lessonName,
            @RequestParam("lessonDate") LocalDate lessonDate,
            @RequestParam("abstractText") String abstractText
    ) {
        try {

            educatorService.updateLesson(lessonId, lessonName, lessonDate, abstractText);
            return "redirect:/educator/courses/" + courseId + "/lessons";
        } catch (ServiceException e) {
            return "redirect:/educator/courses/" + courseId + "/lessons?error=changing_lesson_error";
        }
    }

    @PostMapping("/courses/{courseId}/delete_lesson")
    public String deleteLesson(
            @PathVariable("courseId") Long courseId,
            @RequestParam("lessonId") Long lessonId
    ) {
        educatorService.deleteLesson(lessonId);
        return "redirect:/educator/courses/" + courseId + "/lessons";
    }


}
