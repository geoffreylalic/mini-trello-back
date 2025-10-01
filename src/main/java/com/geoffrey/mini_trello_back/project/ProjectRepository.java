package com.geoffrey.mini_trello_back.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findProjectsByOwnerId(Integer ProfileId);
}
