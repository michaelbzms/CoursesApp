package model;

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
