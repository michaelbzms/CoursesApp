package data;

import Util.Feedback;
import data.jdbc.DataAccess;
import model.User;
import org.springframework.dao.DataAccessException;

public class UsersDAOImplementation implements UsersDAO {

    private final DataAccess dataAccess;

    public UsersDAOImplementation(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    @Override
    public User authenticateUser(String email, String hashedPassword) throws DataAccessException {
        return dataAccess.authenticateUser(email, hashedPassword);
    }

    @Override
    public Feedback changeUserPassword(int userId, String oldHashedPassword, String newHashedPassword) throws DataAccessException {
        return dataAccess.changeUserPassword(userId, oldHashedPassword, newHashedPassword);
    }

}
