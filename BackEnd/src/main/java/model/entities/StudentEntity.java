package model.entities;

import model.Student;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class StudentEntity {

    @Id @Column(name = "idStudents", nullable = false)
    private int id;
    @Column(name = "firstname", nullable = false)
    private String firstName;
    @Column(name = "lastname", nullable = false)
    private String lastName;

    @OneToOne() @JoinColumn(name = "idStudents", nullable = false)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentHasClassesEntity> studentHasClasses = new ArrayList<>();

    public StudentEntity() {}

    public StudentEntity(Student s, boolean generateId){
        if (!generateId) this.id = s.getId();
        this.userEntity = new UserEntity(s);
        this.firstName = s.getFirstName();
        this.lastName = s.getLastName();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public UserEntity getUserEntity() { return userEntity; }

    public void setUserEntity(UserEntity userEntity) { this.userEntity = userEntity; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public List<StudentHasClassesEntity> getStudentHasClasses() { return studentHasClasses; }

    public void setStudentHasClasses(List<StudentHasClassesEntity> classes) { this.studentHasClasses = classes; }

}
