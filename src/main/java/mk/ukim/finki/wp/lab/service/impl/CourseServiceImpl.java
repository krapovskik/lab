package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.*;
import mk.ukim.finki.wp.lab.model.exceptions.*;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.repository.jpa.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final GradeRepository gradeRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, GradeRepository gradeRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<Course> allCourses() {
        return this.courseRepository.findAll();
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        Course course = this.courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        return course.getStudents();
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Course course = this.courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        Student student = this.studentRepository.findByUsername(username).orElseThrow(StudentNotFoundException::new);
        if(course.getStudents().contains(student))
            throw new AlreadyInCourseException();
        course.getStudents().add(student);
        courseRepository.save(course);
        return course;

    }

    @Override
    public void addCourse(String name, String description, String ID, Type type) {
        Teacher teacher = this.teacherRepository.findById(Long.parseLong(ID)).orElseThrow(TeacherNotFoundException::new);
        if(this.courseRepository.findAll().stream().anyMatch(course -> course.getName().equalsIgnoreCase(name)))
            throw new CourseNameException();
        this.courseRepository.save(new Course(name,description,teacher,type));
    }

    @Override
    public Course findById(Long id) {
        return this.courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
    }

    @Override
    @Transactional
    public void delete(Long Id) {
        courseRepository.deleteByCourseId(Id);
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public List<Grade> getStudentsWithGradesByCourse(Long courseId) {
        return gradeRepository.findAll()
                .stream()
                .filter(grade -> grade.getCourse().getCourseId().equals(courseId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> findAll(String text) {
        return courseRepository.findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text,text);
    }
}
