package conf;

import data.*;
import data.jdbc.*;

import java.util.Properties;
import java.util.Set;

public class Configuration {

    // set this to false for easier API testing without needing to login
    public static boolean CHECK_AUTHORISATION;
    public static boolean ALLOW_CORS;    // should allow CORS only for development purposes!

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
        return new CoursesDAOImplementation(dataAccess);
    }

    public StudentsDAO getStudentsDAO() {
        return new StudentsDAOImplementation(dataAccess);
    }

    public UsersDAO getUsersDAO() {
        return new UsersDAOImplementation(dataAccess);
    }

    public long getLoginTTL() {
        return 800000;
    }
}
