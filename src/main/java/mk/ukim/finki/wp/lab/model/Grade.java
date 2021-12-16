package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Character grade;

    @ManyToOne
    private Student student;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public Grade() {
    }

    public Grade(Character grade, Student student, Course course, LocalDateTime timestamp) {
        this.grade = grade;
        this.student = student;
        this.course = course;
        this.timestamp = timestamp;
    }
}
