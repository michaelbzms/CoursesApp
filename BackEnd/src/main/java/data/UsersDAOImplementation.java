package data;

import data.jdbc.DataAccess;

public class UsersDAOImplementation implements UsersDAO {

    private final DataAccess dataAccess;

    public UsersDAOImplementation(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

}
