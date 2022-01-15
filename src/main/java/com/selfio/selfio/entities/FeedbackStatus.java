package com.selfio.selfio.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * The class describes the objects from 'feedback_status' entity.
 */
@Entity
@Table(name = "feedback_status",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
@Getter
@Setter
public class FeedbackStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "statusname")
    private String statusName;
}
