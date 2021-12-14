package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherFullName implements Serializable {

    private String name;

    private String surname;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
