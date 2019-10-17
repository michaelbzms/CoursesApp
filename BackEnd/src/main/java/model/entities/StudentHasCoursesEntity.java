package model.entities;


import javax.persistence.*;

@Entity
@IdClass(StudentHasCoursesId.class)
@Table(name = "students_has_courses")
public class StudentHasCoursesEntity {

    @Id @Column(name = "idStudents", nullable = false)
    private int idStudents;
    @Id @Column(name = "idCourses", nullable = false)
    private int idCourses;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idStudents")
    @JoinColumn(name = "idStudents", referencedColumnName = "idStudents")
    private StudentEntity studentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCourses")
    @JoinColumn(name = "idCourses", referencedColumnName = "idCourses")
    private CourseEntity courseEntity;

    private Double grade;

    public StudentHasCoursesEntity() { }

    public StudentHasCoursesEntity(int sid, int cid, Double grade) {
        this.idStudents = sid;
        this.idCourses = cid;
        this.grade = grade;
    }

    public StudentHasCoursesEntity(StudentEntity s, CourseEntity c, Double grade) {
        this.studentEntity = s;
        this.courseEntity = c;
        this.idStudents = s.getId();
        this.idCourses = c.getId();
        this.grade = grade;
    }

    public int getIdStudents() {
        return idStudents;
    }

    public void setIdStudents(int idStudents) {
        this.idStudents = idStudents;
    }

    public int getIdCourses() {
        return idCourses;
    }

    public void setIdCourses(int idCourses) {
        this.idCourses = idCourses;
    }

    public StudentEntity getStudentEntity() {
        return studentEntity;
    }

    public void setStudentEntity(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
    }

    public CourseEntity getCourseEntity() {
        return courseEntity;
    }

    public void setCourseEntity(CourseEntity courseEntity) {
        this.courseEntity = courseEntity;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

}
