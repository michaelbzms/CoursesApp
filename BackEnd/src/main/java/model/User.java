package model;

import java.util.LinkedHashMap;

public class User {

    private Integer id;        // Might be null for objects with unknown database user id
    private String email;
    private boolean isAdmin;

    public User(Integer id, String email, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.isAdmin = isAdmin;
    }


    public static User getUserFromMap(LinkedHashMap map){
        if (map.get("isAdmin").equals("false")){
            return Student.getStudentFromMap(map);
        }
        return new User((Integer) map.get("id"), (String) map.get("email"), (boolean) map.get("isAdmin"));
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public boolean isAdmin() { return isAdmin; }

    public void setAdmin(boolean admin) { isAdmin = admin; }

}
