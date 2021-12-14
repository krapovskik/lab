package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Type;

import java.util.List;
import java.util.Optional;

public interface CourseService{
    List<Course> allCourses();
    List<Student> listStudentsByCourse(Long courseId);
    Course addStudentInCourse(String username, Long courseId);
    void addCourse(String name, String description, String ID, Type type);
    Course findById(Long id);
    void delete(Long Id);
    void save(Course course);
    List<Grade> getStudentsWithGradesByCourse(Long courseId);
    List<Course> findAll(String text);

}
