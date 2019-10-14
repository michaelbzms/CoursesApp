package conf;

import data.*;
import data.jdbc.*;

import java.util.Properties;
import java.util.Set;

public class Configuration {

    // set this to false for easier API testing without needing to login
    public static boolean CHECK_AUTHORISATION;
    public static boolean ALLOW_CORS;    // should allow CORS only for development purposes!
    public static boolean MOCK_DB;       // mock the existence of a db in memory
    public static boolean USE_JPA;       // use JPA Implementation over JDBCTemplate

    private static final Configuration self = new Configuration();

    private final DataAccess dataAccess = new DataAccess();

    private String contextPath = null;
    private Properties props = new Properties();

    private Configuration() {

    }

    public static Configuration getInstance() {
        return self;
    }

    void setup(String contextPath, Properties props) throws ConfigurationException {
        this.contextPath = contextPath;
        this.props = props;

        CHECK_AUTHORISATION = !("false".equals(getProperty("check_authorisation")));   // true if not specified
        ALLOW_CORS = "true".equals(getProperty("allow_CORS"));                         // false if not specified
        MOCK_DB = "true".equals(getProperty("mock_db"));                               // false if not specified
        USE_JPA = "true".equals(getProperty("useJPA"));                                // false if not specified

        if (!MOCK_DB && !USE_JPA) {
            try {
                dataAccess.setup(
                        getProperty("db.driver"),
                        getProperty("db.url"),
                        getProperty("db.user"),
                        getProperty("db.pass")
                );
            } catch (Exception e) {
                throw new ConfigurationException(e.getMessage(), e);
            }
        }
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getProperty(String name) {
        return getProperty(name, null);
    }

    public String getProperty(String name, String defaultValue) {
        return props.getProperty(name, defaultValue);
    }

    public Set<String> propertyNames() {
        return props.stringPropertyNames();
    }

    public CoursesDAO getCoursesDAO() {
        if (MOCK_DB) return new CoursesDAOMockImpl();
        else if (USE_JPA) return new CoursesDAO_JPAImpl();
        else return new CoursesDAOImplementation(dataAccess);
    }

    public StudentsDAO getStudentsDAO() {
        if (MOCK_DB) return new StudentsDAOMockImpl();
        else if (USE_JPA) return new StudentsDAO_JPAImpl();
        else return new StudentsDAOImplementation(dataAccess);
    }

    public UsersDAO getUsersDAO() {
        if (MOCK_DB) return new UsersDAOMockImpl();
        else if (USE_JPA) return new UsersDAO_JPAImpl();
        else return new UsersDAOImplementation(dataAccess);
    }

    public long getLoginTTL() {
        return 800000;
    }
}
