package data.jdbc;

import Util.Feedback;
import model.Course;
import model.Student;
import model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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

    /* GENERAL */
    private boolean checkIfEmailExists(String email) throws DataAccessException {
        boolean exists;
        try {
            Integer res = jdbcTemplate.queryForObject("SELECT 1 FROM users WHERE email = ?", new Object[]{email}, Integer.class);
            exists = (res != null && res == 1);  // should be true but just in case
        } catch (EmptyResultDataAccessException e) {
            exists = false;
        } catch (IncorrectResultSizeDataAccessException e) {
            System.err.println("(!) WARNING: Same email for multiple users detected!");
            exists = true;                       // this means that there are more than one (which would render our database wrong)
        }
        return exists;
    }

    private boolean checkIfUserIdExists(int userId) throws DataAccessException {
        boolean exists;
        try {
            Integer res = jdbcTemplate.queryForObject("SELECT 1 FROM users WHERE idUsers = ?", new Object[]{userId}, Integer.class);
            exists = (res != null && res == 1);  // should be true but just in case
        } catch (EmptyResultDataAccessException e) {
            exists = false;
        }
        return exists;
    }

    private boolean checkIfPasswordIsCorrect(int userId, String hashedPassword) throws DataAccessException {
        boolean correct;
        try {
            Integer res = jdbcTemplate.queryForObject("SELECT 1 FROM users WHERE idUsers  = ? AND password = ?", new Object[]{userId, hashedPassword}, Integer.class);
            correct = (res != null && res == 1);  // should be true but just in case
        } catch (EmptyResultDataAccessException e) {
            correct = false;
        }
        return correct;
    }


    /* USERS */
    public User authenticateUser(String email, String hashedPassword) throws DataAccessException {
        try {
            // try to authenticate a Student
            return jdbcTemplate.queryForObject("SELECT u.*, s.* FROM users u, students s WHERE u.idUsers = s.idStudents AND u.email = ? AND u.password = ? AND isAdmin = false",
                    new Object[]{email, hashedPassword}, new StudentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            try {
                // if not a student then try to authenticate an admin
                return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ? AND password = ? AND isAdmin = true",
                        new Object[]{email, hashedPassword}, new UserRowMapper());
            } catch (EmptyResultDataAccessException ignored){
            } catch (IncorrectResultSizeDataAccessException e2){
                System.err.println("(!) WARNING: Same email for multiple users detected!");
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            System.err.println("(!) WARNING: Same email for multiple users detected!");
        }
        return null;
    }

    public Feedback changeUserPassword(int userId, String oldHashedPassword, String newHashedPassword) throws DataAccessException {
        Integer code = transactionTemplate.execute(status -> {
            // check if user exists
            if (!checkIfUserIdExists(userId)){
                return -1;
            }
            // given old password must be correct
            if (!checkIfPasswordIsCorrect(userId, oldHashedPassword)){
                return -2;
            }
            jdbcTemplate.update("UPDATE users SET password = ? WHERE idUsers = ?", newHashedPassword, userId);
            return 0;
        });
        if (code != null && code == -1) return new Feedback(false, -1, "User does not exist");
        else if (code != null && code == -2) return new Feedback(false, -2, "Given password is incorrect");
        return new Feedback(true);
    }

    public Student getStudent(int studentId) throws DataAccessException {
        try {
            return jdbcTemplate.queryForObject("SELECT u.*, s.* FROM users u, students s WHERE u.idUsers = s.idStudents AND s.idStudents = ?", new Object[]{studentId}, new StudentRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    /* STUDENTS */
    public List<Student> getALlStudents() throws DataAccessException {
        return jdbcTemplate.query("SELECT u.*, s.* FROM users u, students s WHERE u.idUsers = s.idStudents", new StudentRowMapper());
    }

    public Feedback registerStudent(Student student, String hashedPassword) throws DataAccessException {
        Boolean success = transactionTemplate.execute(status -> {
            // check if email exists
            if (checkIfEmailExists(student.getEmail())){
                return false;
            }
            // insert into user and keep id
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO users (idUsers, email, password, isAdmin) VALUES (default, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, student.getEmail());
                ps.setString(2, hashedPassword);
                ps.setBoolean(3, false);   // never register an admin by accident / trick for security
                return ps;
            }, keyHolder);
            Integer userId = null;
            try {
                userId = keyHolder.getKey().intValue();
            } catch (NullPointerException ignored) { }
            if (userId == null) {
                System.err.println("(!) WARNING: Could not get generated keys in insert student to db!");
                // do not return so that we get exception on next update
            }
            jdbcTemplate.update("INSERT INTO students(idStudents, firstname, lastname) values (?, ?, ?)", userId, student.getFirstName(), student.getLastName());
            return true;
        });
        if (success != null && !success) return new Feedback(false, "Email given is taken");
        return new Feedback(true);
    }

    public Feedback editStudent(Student newStudent) throws DataAccessException {
        Integer code = transactionTemplate.execute(status -> {
            Student oldStudent = getStudent(newStudent.getId());
            if (oldStudent == null) return -1;
            String email = (newStudent.getEmail() != null) ? newStudent.getEmail() : oldStudent.getEmail();
            String firstname = (newStudent.getFirstName() != null) ? newStudent.getFirstName() : oldStudent.getFirstName();
            String lastname = (newStudent.getLastName() != null) ? newStudent.getLastName() : oldStudent.getLastName();
            // check if email exists
            if (checkIfEmailExists(newStudent.getEmail())){
                return -2;
            }
            jdbcTemplate.update("UPDATE users SET email = ? WHERE idUsers = ? AND isAdmin = false", email, newStudent.getId());
            jdbcTemplate.update("UPDATE students SET firstname = ?, lastname = ? WHERE idStudents = ?", firstname, lastname, newStudent.getId());
            return 0;
        });
        if (code != null && code == -1) return new Feedback(false, -1, "Student does not exist");
        else if (code != null && code == -2) return new Feedback(false, -2, "New email is taken");
        return new Feedback(true);
    }

    public Feedback deleteStudent(int studentId) {
        Boolean success = transactionTemplate.execute(status -> {
            Student oldStudent = getStudent(studentId);
            if (oldStudent == null) return false;
            jdbcTemplate.update("DELETE FROM students WHERE idStudents = ?", studentId);
            jdbcTemplate.update("DELETE FROM users WHERE idUsers = ? AND isAdmin = false", studentId);
            jdbcTemplate.update("DELETE FROM students_has_courses WHERE idStudents = ?", studentId);
            return true;
        });
        if (success != null && !success) return new Feedback(false, "Student does not exist");
        return new Feedback(true);
    }


    /* COURSES */
    public Course getCourse(int courseId) throws DataAccessException {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM courses WHERE idCourses = ?", new Object[]{courseId}, new CourseRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Course getCourse(int courseId, int studentId) throws DataAccessException {
        String sql1 = "SELECT c.*, shc.grade FROM courses c, students_has_courses shc " +
                      "WHERE c.idCourses = ? AND shc.idCourses = c.idCourses AND shc.idStudents = ?";
        String sql2 = "SELECT *, -1.0 AS \"grade\" FROM courses WHERE idCourses = ?";
        try {
            // first try for a course with a grade for given studentId
            return jdbcTemplate.queryForObject(sql1, new Object[]{courseId, studentId}, new CourseForStudentRowMapper());
        } catch (EmptyResultDataAccessException e){
            try {
                // if not exists then try for a course without one
                return jdbcTemplate.queryForObject(sql2, new Object[]{courseId}, new CourseForStudentRowMapper());
            } catch (EmptyResultDataAccessException e2){
                return null;
            }
        }
    }

    public List<Course> getAllCourses() throws DataAccessException {
        return jdbcTemplate.query("SELECT * FROM courses ORDER BY semester, title", new CourseRowMapper());
    }

    public List<Course> getAllCourses(int studentId) throws DataAccessException {
        // TODO: Is there a faster query?
        String sql = "SELECT *\n" +
                     "FROM ((SELECT c.*, shc.grade\n" +
                     "      FROM courses c, students_has_courses shc\n" +
                     "      WHERE c.idCourses = shc.idCourses AND shc.idStudents = ?)\n" +
                     "        UNION\n" +
                     "      (SELECT *, -1.0 AS grade FROM courses WHERE idCourses NOT IN\n" +
                     "      (SELECT c.idCourses\n" +
                     "      FROM courses c, students_has_courses shc\n" +
                     "      WHERE c.idCourses = shc.idCourses AND shc.idStudents = ?))\n" +
                     "     ) AS courses\n" +
                     "ORDER BY courses.semester, courses.title;";
        return jdbcTemplate.query(sql, new Object[]{studentId, studentId}, new CourseForStudentRowMapper());
    }

    public void submitCourse(Course course) throws DataAccessException {
        // TODO: add E1, ..., E6 options
        String sql = "INSERT INTO courses (idCourses, title, ects, semester, category, type) " +
                     "VALUES (default, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, course.getTitle(), course.getEcts(), course.getSemester(), course.getCategory(), course.getType());
    }

    public Feedback editCourse(Course newCourse) throws DataAccessException {
        Boolean success = transactionTemplate.execute(status -> {
            Course oldCourse = getCourse(newCourse.getId());
            if (oldCourse == null) return false;
            String title = (newCourse.getTitle() != null) ? newCourse.getTitle() : oldCourse.getTitle();
            int ects = (newCourse.getEcts() != -1) ? newCourse.getEcts() : oldCourse.getEcts();
            int semester  = (newCourse.getSemester() != -1) ? newCourse.getSemester() : oldCourse.getSemester();
            String category = (newCourse.getCategory() != null) ? newCourse.getCategory() : oldCourse.getCategory();
            String type = (newCourse.getType() != null) ? newCourse.getType() : oldCourse.getType();
            // TODO: add E1, ..., E6 options
            jdbcTemplate.update("UPDATE courses SET title = ?, ects = ?, semester = ?, category = ?, type = ? WHERE idCourses = ?",
                                     title, ects, semester, category, type, newCourse.getId());
            return true;
        });
        if (success != null && !success) return new Feedback(false, "Course that does not exist");
        else return new Feedback(true);
    }

    public Feedback deleteCourse(int courseId) throws DataAccessException {
        Boolean success = transactionTemplate.execute(status -> {
            Course oldCourse = getCourse(courseId);
            if (oldCourse == null) return false;
            jdbcTemplate.update("DELETE FROM courses WHERE idCourses = ?", courseId);
            jdbcTemplate.update("DELETE FROM students_has_courses WHERE idCourses = ?", courseId);
            return true;
        });
        if (success != null && !success) return new Feedback(false, "Course that does not exist");
        return new Feedback(true);
    }

    public void setGradeForCourse(int studentId, int courseId, Double grade) throws DataAccessException {
        transactionTemplate.execute(status -> {
            boolean gradeExists;
            try {
                Integer res = jdbcTemplate.queryForObject("SELECT 1 FROM students_has_courses WHERE idStudents = ? AND idCourses = ?", new Object[]{studentId, courseId}, Integer.class);
                gradeExists = (res != null && res == 1);   // should be true just in case
            } catch (EmptyResultDataAccessException e){
                gradeExists = false;
            }
            if (gradeExists && grade != null){
                // update existing grade
                jdbcTemplate.update("UPDATE students_has_courses SET grade = ? WHERE idStudents = ? AND idCourses = ?", grade, studentId, courseId);
            } else if (gradeExists && grade == null){
                // delete existing grade
                jdbcTemplate.update("DELETE FROM students_has_courses WHERE idStudents = ? AND idCourses = ?", studentId, courseId);
            } else if (grade != null){
                // insert new grade
                jdbcTemplate.update("INSERT INTO students_has_courses (idStudents, idCourses, grade) VALUES (?, ?, ?)", studentId, courseId, grade);
            }   // else there is nothing to do
            return true;
        });
    }

}
