package model;

public class Course {

    private Integer id;             // Might be null for objects with unknown database user id
    private String title;
    private int ects;
    private String path;
    private String type = null;
    private String specificpath = null;
    private Double grade;           // to be associated with student-user in session

    public Course(Integer id, String title, int ects, String path, Double grade){
        this.id = id;
        this.title = title;
        this.ects = ects;
        this.path = path;
        this.grade = grade;
    }

    public Course(Integer id, String title, int ects, String path, Double grade, String type, String specificpath){
        this.id = id;
        this.title = title;
        this.ects = ects;
        this.path = path;
        this.grade = grade;
        this.type = type;
        this.specificpath = specificpath;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getEcts() { return ects; }

    public void setEcts(int ects) { this.ects = ects; }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    public Double getGrade() { return grade; }

    public void setGrade(Double grade) { this.grade = grade; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getSpecificpath() { return specificpath; }

    public void setSpecificpath(String specificpath) { this.specificpath = specificpath; }

}
