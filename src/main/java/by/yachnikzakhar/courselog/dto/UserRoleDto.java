package by.yachnikzakhar.courselog.dto;

import by.yachnikzakhar.courselog.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDto {

    private Integer id;

    private String roleName;

}
