package model.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudentHasClassesIdEmbeddable implements Serializable {

    // TODO: Mapping of these fields uses the default name instead of the one in db -> must fix (but how?)
    @Column(name = "idStudents")  // <- DOES NOT WORK
    private int idStudents;
    @Column(name = "idCourses")   // <- DOES NOT WORK
    private int idCourses;

    public StudentHasClassesIdEmbeddable() {}

    public StudentHasClassesIdEmbeddable(int idStudents, int idCourses) {
        this.idStudents = idStudents;
        this.idCourses = idCourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentHasClassesIdEmbeddable that = (StudentHasClassesIdEmbeddable) o;
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
