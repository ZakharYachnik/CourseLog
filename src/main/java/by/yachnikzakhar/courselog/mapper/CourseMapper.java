package by.yachnikzakhar.courselog.mapper;

import by.yachnikzakhar.courselog.dto.CourseDto;
import by.yachnikzakhar.courselog.model.Course;
import by.yachnikzakhar.courselog.model.User;

import java.util.ArrayList;
import java.util.List;

public class CourseMapper {

    private static CourseMapper instance;

    private CourseMapper(){
    }

    public CourseDto courseToCourseDto(Course course){
        return CourseDto.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .groupCode(course.getGroupCode())
                .educator(getEducator(course.getUsers()))
                .build();
    }

    public static CourseMapper getInstance() {
        if(instance == null) {
            instance = new CourseMapper();
        }
        return instance;
    }

    public List<CourseDto> coursesToCourseDtos(List<Course> courses){
        List<CourseDto> courseDtos = new ArrayList<>();
        for(Course course : courses) {
            if(course.getStatus().equals("ACTIVE")) {
                courseDtos.add(courseToCourseDto(course));
            }
        }
        return courseDtos;
    }

    private User getEducator(List<User> users) {
        for(User user : users) {
            if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("EDUCATOR"))) {
                return user;
            }
        }
        return null;
    }
}
