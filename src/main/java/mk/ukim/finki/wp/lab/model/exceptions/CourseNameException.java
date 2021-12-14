package mk.ukim.finki.wp.lab.model.exceptions;

public class CourseNameException extends RuntimeException{
    public CourseNameException() {
        super("You can't add course with same name. Try add new one.");
    }
}
