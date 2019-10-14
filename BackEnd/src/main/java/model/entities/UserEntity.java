package model.entities;

import model.User;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id @GeneratedValue @Column(name = "idUsers", nullable = false)
    private int id;        // Might be null for objects with unknown database user id
    @Column(length = 128, nullable = false)
    private String email;
    @Column(nullable = false)
    private boolean isAdmin;

    public UserEntity() { }

    public UserEntity(User u) {
        this.id = u.getId();
        this.email = u.getEmail();
        this.isAdmin = u.isAdmin();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public boolean isAdmin() { return isAdmin; }

    public void setAdmin(boolean admin) { isAdmin = admin; }

}
