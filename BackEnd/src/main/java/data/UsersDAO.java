package data;

import Util.Feedback;
import model.User;
import org.springframework.dao.DataAccessException;

public interface UsersDAO {

    User authenticateUser(String email, String hashedPassword) throws DataAccessException;

    Feedback changeUserPassword(int userId, String oldHashedPassword, String newHashedPassword) throws DataAccessException;

}
