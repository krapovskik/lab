package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;

import java.time.LocalDateTime;
import java.util.List;

public interface GradeService{
    void save(Character grade, Student student, Course course, LocalDateTime timestamp);
    List<Grade> findAllBetween(LocalDateTime from,LocalDateTime to);
}
