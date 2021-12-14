package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Type;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNameException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import mk.ukim.finki.wp.lab.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final TeacherService teacherService;
    private final TypeService typeService;

    public CourseController(CourseService courseService, TeacherService teacherService, TypeService typeService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.typeService = typeService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error,
                                 @RequestParam(required = false) String type,
                                 @RequestParam(required = false) String filter,
                                 Model model
                                 ){
        if(error != null){
            model.addAttribute("hasError",true);
            model.addAttribute("errorMessage",error);
        }
        if(type != null){
            model.addAttribute("listCourses",this.courseService.allCourses().stream()
                    .filter(course -> course.getType() == Type.valueOf(type)).collect(Collectors.toList()));
        }
        else {
            model.addAttribute("listCourses", this.courseService.allCourses().stream().sorted(Comparator.comparing(Course::getName)).collect(Collectors.toList()));
        }
        if(filter != null){
            model.addAttribute("listCourses",this.courseService.findAll(filter));
        }
        model.addAttribute("types",this.typeService.getAllTypes());
        return "listCourses";
    }

    @PostMapping
    public String setCourseId(@RequestParam(required = false) String courseId, HttpServletRequest request){
        request.getSession().setAttribute("courseId", courseId);
        return "redirect:/addStudent";
    }

    @GetMapping("/add")
    public String getAddCourse(Model model){
        model.addAttribute("teachers",this.teacherService.findAll());
        model.addAttribute("types",this.typeService.getAllTypes());
        return "add-course";
    }

    @PostMapping("/add")
    public String saveCourse(@RequestParam(required = false) String courseId,
                             @RequestParam String name,
                             @RequestParam String desc,
                             @RequestParam String type,
                             @RequestParam String teachers){
        try {
            if(courseId == null)
                this.courseService.addCourse(name, desc, teachers, Type.valueOf(type));
            else {
                Course course = this.courseService.findById(Long.parseLong(courseId));
                course.setName(name);
                course.setDescription(desc);
                course.setTeacher(this.teacherService.findById(Long.parseLong(teachers)));
                course.setType(Type.valueOf(type));
                courseService.save(course);
            }
        }catch (CourseNameException e){
            return "redirect:/courses?error=" + e.getMessage();
        }

        return "redirect:/courses";
    }

    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable Long id,Model model){
        if(this.courseService.findById(id) != null) {
            model.addAttribute("course", this.courseService.findById(id));
            model.addAttribute("teachers", this.teacherService.findAll());
            model.addAttribute("types",this.typeService.getAllTypes());
            return "add-course";
        }
        return "redirect:/courses?error=CourseNotFound";
    }

    @GetMapping ("/delete/{id}")
    public String deleteCourse(@PathVariable Long id){
        this.courseService.delete(id);
        return "redirect:/courses";
    }
}
