package model;

import model.entities.CourseEntity;

public class Course {

    private Integer id;             // Might be null for objects with unknown database user id
    private String title;
    private int ects;
    private int semester;
    private String category;
    private String type;
    private Double grade;          // to be associated with student-user in session
    private boolean[] E;

    public Course(Integer id, String title, int ects, int semester, String category, Double grade, String type){
        this.id = id;
        this.title = title;
        this.ects = ects;
        this.semester = semester;
        this.category = category;
        this.grade = grade;
        this.type = type;
    }

    public Course(Integer id, String title, int ects, int semester, String category, Double grade, String type, boolean[] E){
        this.id = id;
        this.title = title;
        this.ects = ects;
        this.semester = semester;
        this.category = category;
        this.grade = grade;
        this.type = type;
        this.E = E;
    }

    public Course(CourseEntity ce){
        this.id = ce.getId();
        this.title = ce.getTitle();
        this.ects = ce.getEcts();
        this.semester = ce.getSemester();
        this.category = ce.getCategory();
        this.type = ce.getType();
        this.E = new boolean[]{ce.isE1(), ce.isE2(), ce.isE3(), ce.isE4(), ce.isE5(), ce.isE6()};
    }

    public Course(CourseEntity ce, double grade){
        this.id = ce.getId();
        this.title = ce.getTitle();
        this.ects = ce.getEcts();
        this.semester = ce.getSemester();
        this.category = ce.getCategory();
        this.type = ce.getType();
        this.E = new boolean[]{ce.isE1(), ce.isE2(), ce.isE3(), ce.isE4(), ce.isE5(), ce.isE6()};
        this.grade = grade;
    }


    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getEcts() { return ects; }

    public void setEcts(int ects) { this.ects = ects; }

    public int getSemester() { return semester; }

    public void setSemester(int semester) { this.semester = semester; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public Double getGrade() { return grade; }

    public void setGrade(Double grade) { this.grade = grade; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public final boolean[] getE() {
        return E;
    }

    public void setE(boolean[] e) {
        E = e;
    }

}
