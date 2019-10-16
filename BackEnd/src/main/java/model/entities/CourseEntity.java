package model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@NamedQueries({
    @NamedQuery(name="selectallcourses", query="SELECT c FROM CourseEntity c")
})
public class CourseEntity {

    @Id @Column(name = "idCourses", nullable = false) @GeneratedValue
    private int id;      // Might be null for objects with unknown database user id
    @Column(nullable = false) private String title;
    @Column(nullable = false) private int ects;
    @Column(nullable = false) private int semester;
    @Column(nullable = false) private String category;
    @Column(nullable = false) private String type;
    @Column(nullable = false) private boolean E1;
    @Column(nullable = false) private boolean E2;
    @Column(nullable = false) private boolean E3;
    @Column(nullable = false) private boolean E4;
    @Column(nullable = false) private boolean E5;
    @Column(nullable = false) private boolean E6;

    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StudentHasClassesEntity> studentHasClasses = new ArrayList<>();

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getEcts() { return ects; }

    public void setEcts(int ects) { this.ects = ects; }

    public int getSemester() { return semester; }

    public void setSemester(int semester) { this.semester = semester; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public boolean isE1() { return E1; }

    public void setE1(boolean e1) { E1 = e1; }

    public boolean isE2() { return E2; }

    public void setE2(boolean e2) { E2 = e2; }

    public boolean isE3() { return E3; }

    public void setE3(boolean e3) { E3 = e3; }

    public boolean isE4() { return E4; }

    public void setE4(boolean e4) { E4 = e4; }

    public boolean isE5() { return E5; }

    public void setE5(boolean e5) { E5 = e5; }

    public boolean isE6() { return E6; }

    public void setE6(boolean e6) { E6 = e6; }

    public List<StudentHasClassesEntity> getStudentHasClasses() { return studentHasClasses; }

    public void setStudentHasClasses(List<StudentHasClassesEntity> studentHasClasses) { this.studentHasClasses = studentHasClasses; }

}
