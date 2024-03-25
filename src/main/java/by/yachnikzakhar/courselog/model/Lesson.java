package by.yachnikzakhar.courselog.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "lesson_name")
    private String lessonName;

    @Column(name = "abstract_text")
    private String abstractText;

    @Column(name = "lesson_date")
    private LocalDate lessonDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "courses_id")
    private Course course;
}
