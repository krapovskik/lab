package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> searchByNameOrSurname(String text) {
        return studentRepository.findAllByNameOrSurname(text,text);
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        Student student = new Student(username, password, name, surname);
        if(this.studentRepository.findAll().contains(student)){
            this.courseRepository.findAll()
                    .forEach(course -> course.getStudents().removeIf(s -> s.getUsername().equals(username)));
            this.studentRepository.findAll().remove(student);
        }
        return studentRepository.save(student);
    }

    @Override
    public List<Student> searchByAll(String text) {
        return this.studentRepository.findAllByNameOrSurname(text,text);
    }

    @Override
    public Optional<Student> findByUsername(String username) {
        return studentRepository.findByUsername(username);
    }


}
