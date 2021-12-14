package com.selfio.selfio.entities;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "history")
public class History {
    @Id
    private Integer id;

    @Column
    private Timestamp loginTime;

    @Column
    private Timestamp logoutTime;

    @Column
    private Integer scenes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public History(Integer id, Timestamp loginTime, Timestamp logoutTime, Integer scenes, Integer attempts, Integer interval) {
        this.id = id;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.scenes = scenes;
        this.attempts = attempts;
        this.interval = interval;
    }

    @Column
    private Integer attempts;

    @Column
    private Integer interval;

    public History() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Integer getScenes() {
        return scenes;
    }

    public void setScenes(Integer scenes) {
        this.scenes = scenes;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }
}
