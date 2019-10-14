package model.entities;

import model.Student;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@NamedQueries({
    @NamedQuery(name="selectall",
            query="SELECT s FROM StudentEntity s")
})
public class StudentEntity {

    @Id @Column(name = "idStudents", nullable = false, updatable = false)
    private int id;
    @Column(name = "firstname", nullable = false)
    private String firstName;
    @Column(name = "lastname", nullable = false)
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) @JoinColumn(name = "idStudents", nullable = false)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentHasClassesEntity> studentHasClasses = new ArrayList<>();

    public StudentEntity() {}

    public StudentEntity(Student s){
        if (s.getId() == null) { System.out.println("(!) -> Warning: null id in entity"); s.setId(0); }
        this.id = s.getId();
        this.userEntity = new UserEntity(s, false);
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
