package model.entities;

import model.Course;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@NamedQueries({
    @NamedQuery(name="selectallcourses", query="SELECT c FROM CourseEntity c ORDER BY c.semester, c.title")
})
public class CourseEntity {

    @Id @Column(name = "idCourses", nullable = false) @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    private List<StudentHasCoursesEntity> studentHasClasses = new ArrayList<>();

    public CourseEntity() { }

    public CourseEntity(Course c) {
        if (c.getId() != null) this.id = c.getId();
        this.title = c.getTitle();
        this.ects = c.getEcts();
        this.semester = c.getSemester();
        this.category = c.getCategory();
        this.type = c.getType();
        boolean[] E = c.getE();
        if (E != null) {
            this.E1 = E[0];
            this.E2 = E[1];
            this.E3 = E[2];
            this.E4 = E[3];
            this.E5 = E[4];
            this.E6 = E[5];
        } else {
            this.E1 = false;
            this.E2 = false;
            this.E3 = false;
            this.E4 = false;
            this.E5 = false;
            this.E6 = false;
        }
    }

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

    public List<StudentHasCoursesEntity> getStudentHasClasses() { return studentHasClasses; }

    public void setStudentHasClasses(List<StudentHasCoursesEntity> studentHasClasses) { this.studentHasClasses = studentHasClasses; }

}
