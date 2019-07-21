package data;

import data.jdbc.DataAccess;

public class StudentsDAOImplementation implements StudentsDAO {

    private final DataAccess dataAccess;

    public StudentsDAOImplementation(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

}
