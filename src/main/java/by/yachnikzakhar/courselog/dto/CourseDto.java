package by.yachnikzakhar.courselog.dto;

import by.yachnikzakhar.courselog.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class CourseDto {

    private Integer id;

    private String courseName;

    private String groupCode;

    private User educator;
}
