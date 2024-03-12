package by.yachnikzakhar.courselog.dto;

import by.yachnikzakhar.courselog.model.Course;
import by.yachnikzakhar.courselog.model.UserRole;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;

    private String username;

    private boolean active;

    private String firstName;

    private String secondName;

    private String phoneNumber;

    private List<UserRole> userRoles;

    private List<Course> courses;

}
