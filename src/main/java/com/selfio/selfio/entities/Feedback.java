package com.selfio.selfio.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "feedback",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
@Getter
@Setter
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "userid")
    private Integer userId;

    @Column(name = "messagetype")
    private Integer messageType;

    @Column(name = "text_")
    private String text;

    @Column(name = "statuscode")
    private Integer statusCode;
}
