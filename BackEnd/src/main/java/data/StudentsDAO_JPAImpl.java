package data;

import Util.Feedback;
import data.jpa.JPAUtil;
import model.Student;
import model.entities.StudentEntity;
import model.entities.UserEntity;
import org.springframework.dao.DataAccessException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class StudentsDAO_JPAImpl implements StudentsDAO {

    @Override
    public Student getStudent(int studentId) throws DataAccessException {
        Student s = null;
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return null; }
        try {
            s = new Student(em.getReference(StudentEntity.class, studentId));
        } catch (EntityNotFoundException ignored) {
        } catch(Exception e) {
            e.printStackTrace();
            throw e;   // throw it again
        } finally {
            em.close();
        }
        return s;
    }


    @Override
    public List<Student> getALlStudents() throws DataAccessException {
        List<Student> l = new ArrayList<>();
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return null; }
        try {
            List<StudentEntity> res =(List<StudentEntity>) em.createNamedQuery("selectall").getResultList();
            for (StudentEntity s : res) {
                l.add(new Student(s));
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
    public Feedback registerStudent(Student student, String hashedPassword) throws DataAccessException {
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return null; }
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            List res = em.createNativeQuery("SELECT 1 FROM users WHERE email = ?").setParameter(1, student.getEmail()).getResultList();
            if (res.isEmpty()) {
                UserEntity ue = new UserEntity(student);
                em.persist(ue);
                em.flush();
                StudentEntity se = new StudentEntity(student, ue);
                em.persist(se);
                em.flush();
                em.createNativeQuery("UPDATE users SET password = ? WHERE idUsers = ?")
                        .setParameter(1, hashedPassword)
                        .setParameter(2, se.getId())
                        .executeUpdate();
            } else {
                tx.commit();
                return new Feedback(false, "Email is already taken");
            }
            tx.commit();
        } catch (EntityNotFoundException ignored) {
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
    public Feedback editStudent(Student student) throws DataAccessException {
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return null; }
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            StudentEntity se = em.getReference(StudentEntity.class, student.getId());
            UserEntity ue = se.getUserEntity();
            ue.setEmail(student.getEmail());
            se.setFirstName(student.getFirstName());
            se.setLastName(student.getLastName());
            tx.commit();
        } catch (EntityNotFoundException e) {
            tx.commit();
            return new Feedback(false, "Student does not exist");
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
    public Feedback deleteStudent(int studentId) throws DataAccessException {
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return null; }
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            StudentEntity se = em.getReference(StudentEntity.class, studentId);
            UserEntity ue = se.getUserEntity();
            em.remove(se);
            em.remove(ue);
            tx.commit();
        } catch (EntityNotFoundException e) {
            tx.commit();
            return new Feedback(false, "Student does not exist");
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw e;   // throw it again
        } finally {
            em.close();
        }
        return new Feedback(true);
    }
}
