package model;

import model.entities.StudentEntity;

import java.util.LinkedHashMap;

public class Student extends User {

    private String firstName, lastName;

    public Student(User u, String firstName, String lastName) {
        super(u.getId(), u.getEmail(), u.isAdmin());
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(Integer id, String email, boolean isAdmin, String firstName, String lastName) {
        super(id, email, isAdmin);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(StudentEntity se) {
        super(se.getUserEntity());
        this.firstName = se.getFirstName();
        this.lastName = se.getLastName();
    }

    public static Student getStudentFromMap(LinkedHashMap map) {
        return new Student((Integer) map.get("id"), (String) map.get("email"), (boolean) map.get("isAdmin"), (String) map.get("firstName"), (String) map.get("lastName"));
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

}
