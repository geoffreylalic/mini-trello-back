package com.geoffrey.mini_trello_back.project;

import com.geoffrey.mini_trello_back.Task.Task;
import com.geoffrey.mini_trello_back.profile.Profile;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "project")
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile owner;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
}
