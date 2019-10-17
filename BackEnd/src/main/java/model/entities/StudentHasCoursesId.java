package model.entities;

import java.io.Serializable;
import java.util.Objects;


public class StudentHasCoursesId implements Serializable {

    private int idStudents;
    private int idCourses;

    public StudentHasCoursesId() {}

    public StudentHasCoursesId(int idStudents, int idCourses) {
        this.idStudents = idStudents;
        this.idCourses = idCourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentHasCoursesId that = (StudentHasCoursesId) o;
        return Objects.equals(this.idStudents, that.idStudents) && Objects.equals(this.idCourses, that.idCourses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStudents, idCourses);
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

}
