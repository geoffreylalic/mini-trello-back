package com.geoffrey.mini_trello_back.profile;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.geoffrey.mini_trello_back.project.Project;
import com.geoffrey.mini_trello_back.task.Task;
import com.geoffrey.mini_trello_back.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Table(name = "profiles")
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<Project> projects;

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Task> tasks;

    public Profile(User user, LocalDate dateOfBirth) {
        this.user = user;
        this.dateOfBirth = dateOfBirth;
    }

    public Profile() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
