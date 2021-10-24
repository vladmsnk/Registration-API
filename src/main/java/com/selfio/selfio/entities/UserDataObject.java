package com.selfio.selfio.entities;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class UserDataObject {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Integer id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean verified;

    public UserDataObject() {

    }

    public UserDataObject(String email, String password, Boolean verified) {
        this.email = email;
        this.password = password;
        this.verified = verified;
    }

    public UserDataObject(Integer id, String email, String password, Boolean verified) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.verified = verified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataObject that = (UserDataObject) o;
        return id.equals(that.id) && email.equals(that.email) && password.equals(that.password) && verified.equals(that.verified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, verified);
    }
}
