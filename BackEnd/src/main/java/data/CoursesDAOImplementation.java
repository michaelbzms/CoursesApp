package data;

import data.jdbc.DataAccess;

public class CoursesDAOImplementation implements CoursesDAO {

    private final DataAccess dataAccess;

    public CoursesDAOImplementation(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

}
