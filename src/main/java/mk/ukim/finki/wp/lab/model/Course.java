package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long courseId;

    String name;

    String description;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Student> students;

    @ManyToOne
    Teacher teacher;

    @Enumerated(EnumType.STRING)
    Type type;

    public Course(){}

    public Course(String name,String description,Teacher teacher,Type type) {
        this.name = name;
        this.description = description;
        this.students = new ArrayList<>();
        this.teacher = teacher;
        this.type = type;
    }
}
