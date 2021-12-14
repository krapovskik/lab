package mk.ukim.finki.wp.lab.model.exceptions;

public class AlreadyInCourseException extends RuntimeException{
    public AlreadyInCourseException() {
        super("This student is already in this course!\n" +
                "Select another student.");
    }
}
