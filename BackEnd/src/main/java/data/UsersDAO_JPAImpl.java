package data;

import Util.Feedback;
import data.jpa.JPAUtil;
import model.Student;
import model.User;
import model.entities.StudentEntity;
import model.entities.UserEntity;
import org.springframework.dao.DataAccessException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class UsersDAO_JPAImpl implements UsersDAO {

    @Override
    public User authenticateUser(String email, String hashedPassword) throws DataAccessException {
        User u = null;
        EntityManager em = JPAUtil.getNewEntityManager();
        if (em == null) { System.err.println("ErRoR: JPA null EntityManager!"); return null; }
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Query q = em.createNativeQuery("SELECT idUsers FROM users WHERE email = ? AND password = ?")
                    .setParameter(1, email)
                    .setParameter(2, hashedPassword);
            List res = q.getResultList();
            if (!res.isEmpty()) {
                int userId = (int) res.get(0);
                StudentEntity se = em.find(StudentEntity.class, userId);
                if (se != null) {   // Student
                    u = new Student(se);
                } else {  // maybe admin
                    UserEntity ue = em.find(UserEntity.class, userId);
                    if (ue != null) {   // admin
                        u = new User(ue);
                    }  // else does not exist
                }
            }
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw e;   // throw it again
        } finally {
            em.close();
        }
        return u;
    }

    @Override
    public Feedback changeUserPassword(int userId, String oldHashedPassword, String newHashedPassword) throws DataAccessException {
        return null;
    }
}
