package data;

import Util.Feedback;
import model.Course;

import java.util.List;
import java.util.Arrays;

public class CoursesDAOMockImpl implements CoursesDAO {

    private Course[] courses = new Course[]{
        new Course(1, "Introduction to Programming", 7, 1, "core", null, "obligatory"),
        new Course(2, "Discrete Math", 7, 1, "core", null, "obligatory"),
        new Course(3, "Logical Drawing", 6, 1, "core", null, "obligatory")
        // TODO: More
    };

    @Override
    public Course getCourse(int courseId) {
        return (courseId > 0 && courseId <= courses.length) ? courses[courseId - 1] : null;
    }

    @Override
    public Course getCourse(int courseId, int studentId) {
        return getCourse(courseId);
    }

    @Override
    public List<Course> getAllCourses() {
        return Arrays.asList(courses);
    }

    @Override
    public List<Course> getAllCourses(int studentId) {
        return getAllCourses();
    }

    @Override
    public void submitCourse(Course course) { }

    @Override
    public Feedback editCourse(Course course) {
        return new Feedback(false, "Cannot edit course in mock mode");
    }

    @Override
    public Feedback deleteCourse(int courseId) {
        return new Feedback(false, "Cannot delete course in mock mode");
    }

    @Override
    public void setGradeForCourse(int studentId, int courseId, Double grade) {
        if (courseId > 0 && courseId <= courses.length) {
            courses[courseId - 1].setGrade(grade);
        }
    }

}
