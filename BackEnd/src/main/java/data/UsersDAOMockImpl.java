package data;

import Util.Feedback;
import model.Student;
import model.User;

public class UsersDAOMockImpl implements UsersDAO {

    @Override
    public User authenticateUser(String email, String hashedPassword) {
        return new Student(0, email, false, "FirrrstName", "Lassst Name");
    }

    @Override
    public Feedback changeUserPassword(int userId, String oldHashedPassword, String newHashedPassword) {
        return new Feedback(false, "Cannot change password in mock mode");
    }

}
