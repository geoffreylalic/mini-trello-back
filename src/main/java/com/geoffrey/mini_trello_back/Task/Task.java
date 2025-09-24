package com.geoffrey.mini_trello_back.Task;

import com.geoffrey.mini_trello_back.profile.Profile;
import com.geoffrey.mini_trello_back.project.Project;
import jakarta.persistence.*;

@Table(name = "task")
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile assignedTo;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
