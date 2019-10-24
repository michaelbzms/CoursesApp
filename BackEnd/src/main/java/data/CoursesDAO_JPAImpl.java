package data;

import Util.Feedback;
import data.jpa.JPAUtil;
import model.Course;
import model.entities.CourseEntity;
import model.entities.StudentHasCoursesEntity;
import model.entities.StudentHasCoursesId;
import org.springframework.dao.DataAccessException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAO_JPAImpl implements CoursesDAO {

    @Override
    public Course getCourse(int courseId) throws DataAccessException {
        Course c = null;
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return null; }
        try {
            c = new Course(em.getReference(CourseEntity.class, courseId));
        } catch (EntityNotFoundException ignored) {
        } catch(Exception e) {
            e.printStackTrace();
            throw e;   // throw it again
        } finally {
            em.close();
        }
        return c;
    }

    @Override
    public Course getCourse(int courseId, int studentId) throws DataAccessException {
        Course c;
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return null; }
        try {
            List res = em.createQuery("SELECT c, shc.grade FROM CourseEntity c, StudentHasCoursesEntity shc " +
                                             "WHERE c.id = ?1 AND  c.id = shc.idCourses AND shc.idStudents = ?2")
                    .setParameter(1, courseId)
                    .setParameter(2, studentId)
                    .getResultList();
            if (!res.isEmpty()) {
                Object[] o = (Object[]) res.get(0);
                c = new Course((CourseEntity) (o[0]), (double) o[1]);
            } else {
                c = new Course(em.getReference(CourseEntity.class, courseId));
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw e;   // throw it again
        } finally {
            em.close();
        }
        return c;
    }

    @Override
    public List<Course> getAllCourses() throws DataAccessException {
        List<Course> l = new ArrayList<>();
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return null; }
        try {
            List<CourseEntity> res = (List<CourseEntity>) em.createNamedQuery("selectallcourses").getResultList();
            for (CourseEntity c : res) {
                l.add(new Course(c));
            }
            return l;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;   // throw it again
        } finally {
            em.close();
        }
    }

    @Override
    public List<Course> getAllCourses(int studentId) throws DataAccessException {
        List<Course> l = new ArrayList<>();
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return null; }
        try {
            // TODO: Is there a faster query?
            String sql = "SELECT *\n" +
                    "FROM ((SELECT c.*, shc.grade\n" +
                    "      FROM courses c, students_has_courses shc\n" +
                    "      WHERE c.idCourses = shc.idCourses AND shc.idStudents = ?)\n" +
                    "        UNION\n" +
                    "      (SELECT *, -1.0 AS grade FROM courses WHERE idCourses NOT IN\n" +
                    "      (SELECT c.idCourses\n" +
                    "      FROM courses c, students_has_courses shc\n" +
                    "      WHERE c.idCourses = shc.idCourses AND shc.idStudents = ?))\n" +
                    "     ) AS courses\n" +
                    "ORDER BY courses.semester, courses.title";
            List res = em.createNativeQuery(sql).setParameter(1, studentId).setParameter(2, studentId).getResultList();
            for (Object[] o : (List<Object[]>) res) {  // each column is a field of o
                l.add(new Course(
                        (Integer) o[0], (String) o[1], (int) o[2], (int) o[3], (String) o[4], (o[12] != null && (double) o[12] != -1.0) ? (Double) o[12] : null, (String) o[5],
                        new boolean[]{(Byte) o[6] != 0, (Byte) o[7] != 0, (Byte) o[8] != 0, (Byte) o[9] != 0, (Byte) o[10] != 0, (Byte) o[11] != 0})
                );
            }
            return l;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;   // throw it again
        } finally {
            em.close();
        }
    }

    @Override
    public void submitCourse(Course course) throws DataAccessException {
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return; }
        EntityTransaction tx = em.getTransaction();
        try {
            CourseEntity c = new CourseEntity(course);
            tx.begin();
            em.persist(c);
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw e;   // throw it again
        } finally {
            em.close();
        }
    }

    @Override
    public Feedback editCourse(Course course) throws DataAccessException {
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return new Feedback(false, "JPA error"); }
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CourseEntity c = em.getReference(CourseEntity.class, course.getId());
            c.setTitle(course.getTitle());
            c.setEcts(course.getEcts());
            c.setSemester(course.getSemester());
            c.setCategory(course.getCategory());
            c.setType(course.getType());
            // TODO: add E1, ..., E6 options
            tx.commit();
        } catch(EntityNotFoundException e) {
            tx.rollback();
            return new Feedback(false, "Course does not exist");
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw e;   // throw it again
        } finally {
            em.close();
        }
        return new Feedback(true);
    }

    @Override
    public Feedback deleteCourse(int courseId) throws DataAccessException {
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return new Feedback(false, "JPA error"); }
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CourseEntity c = em.getReference(CourseEntity.class, courseId);
            em.remove(c);
            tx.commit();
        } catch(EntityNotFoundException e) {
            tx.rollback();
            return new Feedback(false, "Course does not exist");
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw e;   // throw it again
        } finally {
            em.close();
        }
        return new Feedback(true);
    }

    @Override
    public void setGradeForCourse(int studentId, int courseId, Double grade) throws DataAccessException {
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return; }
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            StudentHasCoursesEntity shc = em.find(StudentHasCoursesEntity.class, new StudentHasCoursesId(studentId, courseId));
            if (shc == null && grade != null) {   // if not exists then persist grade
                shc = new StudentHasCoursesEntity(studentId, courseId, grade);
                em.persist(shc);
            } else if (shc != null) {             // if exists remove grade
                em.remove(shc);
            } // else no need to do anything
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw e;   // throw it again
        } finally {
            em.close();
        }
    }
}
