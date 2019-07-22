package data.jdbc;

import model.Course;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;


public class DataAccess {

    private static final int MAX_TOTAL_CONNECTIONS = 16;
    private static final int MAX_IDLE_CONNECTIONS = 8;

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;

    public void setup(String driverClass, String url, String user, String pass) throws SQLException {

        //initialize the data source
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName(driverClass);
        bds.setUrl(url);
        bds.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        bds.setMaxIdle(MAX_IDLE_CONNECTIONS);
        bds.setUsername(user);
        bds.setPassword(pass);
        bds.setValidationQuery("SELECT 1");
        bds.setTestOnBorrow(true);
        bds.setDefaultAutoCommit(true);

        // check that everything works OK
        bds.getConnection().close();

        // Transaction manager
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(bds);

        // initialize the jdbc template utility
        jdbcTemplate = new JdbcTemplate(bds);

        // initialize the transaction template utility
        transactionTemplate = new TransactionTemplate(transactionManager);

        // keep the dataSource for the low-level manual example to function (not actually required)
        dataSource = bds;
    }

    public Course getCourse(int courseId) throws DataAccessException {
        return jdbcTemplate.queryForObject("SELECT * FROM courses WHERE idCourses = ?", new Object[]{courseId}, new CourseRowMapper());
    }

    public Course getCourse(int courseId, int studentId) throws DataAccessException {
        String sql = "SELECT c.*, shc.grade FROM courses c, students_has_courses shc " +
                     "WHERE idCourses = ? AND shc.idCourses = c.idCourses AND shc.idStudents = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{courseId, studentId}, new CourseRowMapper());
    }

    public List<Course> getAllCourses() throws DataAccessException {
        return jdbcTemplate.query("SELECT * FROM courses", new CourseRowMapper());
    }

    public List<Course> getAllCourses(int studentId) throws DataAccessException {
        String sql = "SELECT c.*, shc.grade " +
                     "FROM courses c, students_has_courses shc " +
                     "WHERE c.idCourses = shc.idCourses AND shc.idStudents = ?";
        return jdbcTemplate.query(sql, new Object[]{studentId}, new CourseForStudentRowMapper());
    }

    public void submitCourse(Course course) throws DataAccessException {
        String sql = "INSERT INTO courses (idCourses, title, ects, semester, path, type, specificpath) " +
                     "VALUES (default, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, course.getTitle(), course.getEcts(), course.getSemester(), course.getPath(), course.getType(), course.getSpecificpath());
    }

    public void editCourse(Course newCourse) throws DataAccessException {
        transactionTemplate.execute(status -> {
            Course oldCourse = getCourse(newCourse.getId());
            String title = (newCourse.getTitle() != null) ? newCourse.getTitle() : oldCourse.getTitle();
            int ects = (newCourse.getEcts() != -1) ? newCourse.getEcts() : oldCourse.getEcts();
            int semester  = (newCourse.getSemester() != -1) ? newCourse.getSemester() : oldCourse.getSemester();
            String path = (newCourse.getPath() != null) ? newCourse.getPath() : oldCourse.getPath();
            String type = (newCourse.getType() != null) ? newCourse.getType() : oldCourse.getType();
            String specificpath = (newCourse.getSpecificpath() != null) ? newCourse.getSpecificpath() : oldCourse.getSpecificpath();
            jdbcTemplate.update("UPDATE courses SET title = ?, ects = ?, semester = ?, path = ?, type = ?, specificpath = ? WHERE idCourses = ?",
                                     title, ects, semester, path, type, specificpath, newCourse.getId());
            return true;
        });
    }

    public void deleteCourse(int courseId) throws DataAccessException {
        jdbcTemplate.update("DELETE FROM courses WHERE idCourses = ?", courseId);
    }

}