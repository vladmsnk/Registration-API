package com.selfio.selfio.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = "email")}
)


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @Column
    private String googleID;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "secondname")
    private String secondName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

        public Integer getId () {
            return id;
        }

        public void setId (Integer id){
            this.id = id;
        }

        public String getEmail () {
            return email;
        }

        public void setEmail (String email){
            this.email = email;
        }

        public String getPassword () {
            return password;
        }


        public void setPassword (String password){
            this.password = password;
        }

        public Boolean getVerified () {
            return verified;
        }

        public void setVerified (Boolean verified){
            this.verified = verified;
        }

        public String getGoogleID () {
            return googleID;
        }

        public void setGoogleID (String googleID){
            this.googleID = googleID;
        }

        public String getFirstName () {
            return firstName;
        }

        public void setFirstName (String firstName){
            this.firstName = firstName;
        }

        public String getSecondName () {
            return secondName;
        }

        public void setSecondName (String secondName){
            this.secondName = secondName;
        }

        @Override
        @Transactional
        public boolean equals (Object o){
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(verified, user.verified);
        }

        @Override
        public int hashCode () {
            return Objects.hash(id, email, password, verified);
        }

        @Override
        public String toString () {
            return "User{" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", verified=" + verified +
                    '}';
        }

    }
}
