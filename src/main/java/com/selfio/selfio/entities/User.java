package com.selfio.selfio.entities;


import com.selfio.selfio.validators.ValidEmail;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ValidEmail
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean verified;

    public User() {

    }

    public User(String email, String password, Boolean verified) {
        this.email = email;
        this.password = password;
        this.verified = verified;
    }

    public User(Integer id, String email, String password, Boolean verified) {
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
        User that = (User) o;
        return id.equals(that.id) && email.equals(that.email) && password.equals(that.password) && verified.equals(that.verified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, verified);
    }
}
