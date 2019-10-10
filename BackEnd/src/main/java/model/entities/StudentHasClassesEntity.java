package model.entities;


import javax.persistence.*;

@Entity
@Table(name = "students_has_courses")
public class StudentHasClassesEntity {
    @EmbeddedId
    private StudentHasClassesIdEmbeddable id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idStudents")
    private StudentEntity studentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCourses")
    private CourseEntity courseEntity;

    private Double grade;

    public StudentHasClassesEntity() {}

    public StudentHasClassesEntity(StudentEntity s, CourseEntity c) {
        this.studentEntity = s;
        this.courseEntity = c;
        this.id = new StudentHasClassesIdEmbeddable(s.getId(), c.getId());
    }


    public StudentHasClassesIdEmbeddable getId() {
        return id;
    }

    public void setId(StudentHasClassesIdEmbeddable id) {
        this.id = id;
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