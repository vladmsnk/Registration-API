package com.selfio.selfio.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "feedback_status",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
@Getter
@Setter
public class FeedbackStatus {
    @Id
    private Integer id;

    @Column(name = "statusname")
    private Integer statusName;
}
