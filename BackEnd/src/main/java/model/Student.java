package model;

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

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

}
