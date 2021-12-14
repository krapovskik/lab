package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.converters.TeacherFullNameConverter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = TeacherFullNameConverter.class)
    private TeacherFullName teacherFullName;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfEmployment;

    public Teacher() {
    }

    public Teacher(String name, String surname) {
        this.teacherFullName.setName(name);
        this.teacherFullName.setSurname(surname);
        this.dateOfEmployment = LocalDate.now();
    }

}
