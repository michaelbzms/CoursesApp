package model.entities;

import model.User;

import javax.persistence.*;

@Entity
@Table(name = "users")
@SequenceGenerator(name="seq", initialValue=1)
public class UserEntity {

    @Id @Column(name = "idUsers", nullable = false, updatable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    private int id;
    @Column(length = 128, nullable = false)
    private String email;
    @Column(nullable = false)
    private boolean isAdmin;

    public UserEntity() { }

    public UserEntity(User u) {
        this.email = u.getEmail();
        this.isAdmin = u.isAdmin();
        u.setId(this.id);
    }

    public UserEntity(User u, boolean generateId) {
        if (!generateId) this.id = (u.getId() != null) ? u.getId() : -1;
        this.email = u.getEmail();
        this.isAdmin = u.isAdmin();
        u.setId(this.id);
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public boolean isAdmin() { return isAdmin; }

    public void setAdmin(boolean admin) { isAdmin = admin; }

}
