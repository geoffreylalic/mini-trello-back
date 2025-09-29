package com.geoffrey.mini_trello_back.project;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.geoffrey.mini_trello_back.task.Task;
import com.geoffrey.mini_trello_back.profile.Profile;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "projects")
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "profile_id")
    private Profile owner;

    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<Task> tasks;

    public Project(String name, String description, Profile owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public Project() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Profile getOwner() {
        return owner;
    }

    public void setOwner(Profile owner) {
        this.owner = owner;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
