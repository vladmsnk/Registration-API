package com.selfio.selfio.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "messagetype",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
@Getter
@Setter
public class MessageType {
    @Id
    private Integer id;

    @Column(name = "messagetype")
    private String messageType;
}
