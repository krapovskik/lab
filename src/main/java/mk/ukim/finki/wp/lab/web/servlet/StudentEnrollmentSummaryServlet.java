package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.AlreadyInCourseException;
import mk.ukim.finki.wp.lab.model.exceptions.StudentNotFoundException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "StudentEnrollmentSummaryServlet", value = "/StudentEnrollmentSummary")
public class StudentEnrollmentSummaryServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final CourseService courseService;
    private final GradeService gradeService;
    private final StudentService studentService;

    public StudentEnrollmentSummaryServlet(SpringTemplateEngine springTemplateEngine, CourseService courseService, GradeService gradeService, StudentService studentService) {
        this.springTemplateEngine = springTemplateEngine;
        this.courseService = courseService;
        this.gradeService = gradeService;
        this.studentService = studentService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("student");
        String ID = (String) request.getSession().getAttribute("courseId");
        String grade = request.getParameter("grades");
        LocalDateTime time = LocalDateTime.parse(request.getParameter("time"));
        Course course;
        Grade g;
        try {
            course = this.courseService.addStudentInCourse(username,Long.parseLong(ID));
            Student student = this.studentService.findByUsername(username).orElseThrow(StudentNotFoundException::new);
            gradeService.save(grade.charAt(0),student,course,time);
            WebContext context = new WebContext(request,response, request.getServletContext());
            context.setVariable("course",course);
            context.setVariable("grades",this.courseService.getStudentsWithGradesByCourse(Long.parseLong(ID)));
            request.getSession().invalidate();
            this.springTemplateEngine.process("studentsInCourse.html",context,response.getWriter());
        }catch (StudentNotFoundException e){
            WebContext context = new WebContext(request,response, request.getServletContext());
            context.setVariable("hasError",true);
            context.setVariable("errorMessage",e.getMessage());
            this.springTemplateEngine.process("newStudent.html",context,response.getWriter());
        }catch (AlreadyInCourseException e){
            response.sendRedirect("/addStudent?error=1");
        }
    }
}
