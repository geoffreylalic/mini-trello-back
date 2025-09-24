package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.Task.Task;
import com.geoffrey.mini_trello_back.project.Project;
import com.geoffrey.mini_trello_back.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Table(name = "profile")
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


}
