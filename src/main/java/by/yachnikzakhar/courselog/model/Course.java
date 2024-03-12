package by.yachnikzakhar.courselog.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "status")
    private String status;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public Course(String courseName, String groupCode) {
        this.courseName = courseName;
        this.groupCode = groupCode;
    }
}
