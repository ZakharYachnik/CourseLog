package by.yachnikzakhar.courselog.controller;

import by.yachnikzakhar.courselog.model.Course;
import by.yachnikzakhar.courselog.model.User;
import by.yachnikzakhar.courselog.service.AdminService;
import by.yachnikzakhar.courselog.service.CourseService;
import by.yachnikzakhar.courselog.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private AdminService adminService;


    @GetMapping("/main_page")
    public String showMainPage(){
        return "admin_main_page";
    }

    @GetMapping("add_course")
    public String showAddCourse(){
        return "add_course";
    }

    @PostMapping("add_course")
    public String addCourses(@RequestParam("courseName") String courseName, @RequestParam("groupCode") String groupCode, @RequestParam("usernames") String usernames){
        try {
            courseService.addCourse(courseName, groupCode, usernames);
        }catch (ServiceException e){
            return "redirect:/admin/add_course?error=" + e.getMessage();
        }

        return "redirect:/admin/courses";
    }

    @GetMapping("/courses")
    public String showCourses(Model model){
        try {
            List<Course> courses = courseService.getAllCourses();
            model.addAttribute("courses", courses);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "admin_courses";
    }

    @GetMapping("courses/{id}")
    public String showCourse(@PathVariable("id") Long id, Model model){
        try {
            Course course = courseService.getCourseById(id);
            String educator = courseService.getEducatorUsername(course);
            String usernames = courseService.getUsernames(course);

            model.addAttribute("course", course);
            model.addAttribute("educator", educator);
            model.addAttribute("usernames", usernames);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "admin_course";
    }

    @PostMapping("courses/change_course")
    public String changeCourse(
            @RequestParam("courseName") String courseName,
            @RequestParam("groupCode") String groupCode,
            @RequestParam("usernames") String usernames,
            @RequestParam("educator") String educator
    ){
        try {
            courseService.changeCourse(courseName, groupCode, usernames, educator);
            return "redirect:/admin/courses";
        }catch (ServiceException e){
            return "redirect:/admin/courses?error=" + e.getMessage();
        }
    }

    @PostMapping("courses/complete_course")
    public String completeCourse(@RequestParam("id") Long id){
        try {
            courseService.completeById(id);
            return "redirect:/admin/courses";
        }catch (ServiceException e){
            return "redirect:/admin/courses?error=" + e.getMessage();
        }
    }

    @GetMapping("administration")
    public String showAdministration(
            Model model
    ){
        List<User> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "administration";
    }

    @PostMapping("/administration/block_user")
    public String blockUser(
            @RequestParam("id") Long id
    ){
        try {
            adminService.blockById(id);
            return "redirect:/admin/administration";
        }catch (ServiceException e){
            return "redirect:/admin/administration?error=" + e.getMessage();
        }
    }

    @GetMapping("/administration/add_student")
    public String showAddStudent(){
        return "add_student";
    }

    @PostMapping("/administration/add_student")
    public String addStudent(
            @ModelAttribute("user") User user
    ){
        try {
            adminService.addStudent(user);
            return "redirect:/admin/administration";
        } catch (ServiceException e) {
            return "redirect:/admin/administration?error=" + e.getMessage();
        }
    }

    @GetMapping("/administration/add_educator")
    public String showAddEducator(){
        return "add_educator";
    }

    @PostMapping("/administration/add_educator")
    public String addEducator(
            @ModelAttribute("user") User user
    ){
        try {
            adminService.addEducator(user);
            return "redirect:/admin/administration";
        } catch (ServiceException e) {
            return "redirect:/admin/administration?error=" + e.getMessage();
        }
    }

    @PostMapping("/administration/unblock_user")
    public String unblockUser(
            @RequestParam("id") Long id
    )
    {
        try {
            adminService.unblockById(id);
            return "redirect:/admin/administration";
        }catch (ServiceException e){
            return "redirect:/admin/administration?error=" + e.getMessage();
        }
    }

}
