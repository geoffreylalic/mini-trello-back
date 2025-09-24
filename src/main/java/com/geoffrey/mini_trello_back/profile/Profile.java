package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.task.Task;
import com.geoffrey.mini_trello_back.project.Project;
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

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner")
    private List<Project> projects;

    @OneToMany(mappedBy = "assignedTo")
    private List<Task> tasks;

    public Profile(User user, LocalDate dateOfBirth, Role role) {
        this.user = user;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
    }

    public Profile() {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
