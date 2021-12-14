package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "ListStudentServlet", value = "/addStudent")
public class ListStudentServlet extends HttpServlet {

    private final StudentService studentService;
    private final SpringTemplateEngine springTemplateEngine;

    public ListStudentServlet(StudentService studentService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.springTemplateEngine = springTemplateEngine;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());
        if(request.getParameter("error") != null)
        {
            context.setVariable("hasError",true);
            context.setVariable("errorMessage","This student is already in this course!\n" +
                    "Select another student.");
        }
        String ID = (String) request.getSession().getAttribute("courseId");
        context.setVariable("courseId", ID);
        context.setVariable("listStudents", this.studentService.listAll());
        this.springTemplateEngine.process("listStudents.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        WebContext context = new WebContext(request,response, request.getServletContext());
        if(keyword != null) {
            if (this.studentService.searchByAll(keyword).size() > 0) {
                context.setVariable("listStudents", this.studentService.searchByAll(keyword));
                String ID = (String) request.getSession().getAttribute("courseId");
                context.setVariable("courseId", ID);
                this.springTemplateEngine.process("listStudents.html", context, response.getWriter());
            }
        }
        else
            response.sendRedirect("/addStudent");
    }
}
