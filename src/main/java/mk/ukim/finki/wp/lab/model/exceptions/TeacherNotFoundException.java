package mk.ukim.finki.wp.lab.model.exceptions;

public class TeacherNotFoundException extends RuntimeException{
    public TeacherNotFoundException() {
        super("Teacher not found");
    }
}
