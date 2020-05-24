package com.example.securitydemo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Todo {

    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "task_description")
    private String taskDescription;
    @Column(name = "created_on")
    private Date createdOn;
    @Column(name = "completed_on")
    private Date completedOn;

    public Todo(String taskDescription) {
        this.taskDescription = taskDescription;
        this.createdOn = new Date();
    }

    public Boolean isComplete() {
        return this.completedOn != null;
    }

    @Override
    public String toString() {
        if (this.isComplete()) {
            return String.format("Task: %s\ncreated on: %s\ncompleted on: %s", taskDescription, formatter.format(createdOn), formatter.format(completedOn));
        }
        return String.format("Task: %s\ncreated on: %s\ncompleted: %s", taskDescription, formatter.format(createdOn), this.isComplete());
    }
}
