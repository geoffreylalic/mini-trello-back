package com.geoffrey.mini_trello_back.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findProjectsByOwnerId(Integer ProfileId);

    @Query("""
            SELECT DISTINCT t.project.id
            FROM task t
            WHERE t.assignedTo = :profileId
            """)
    List<Integer> findProjectIdsByProfileId(Integer profileId);


    @Query("""
            SELECT DISTINCT t.project.id
            FROM task t
            WHERE task IN :tasksIds
            """)
    List<Integer> findProjectIdsByTaskIds(List<Integer> taskIds);

    @Query("""
            SELECT DISTINCT p
            FROM Project p
            WHERE p.id IN :projectIds OR p.owner.id = :profileId
            ORDER p.id ASC
            """)
    Page<Project> findProjectsRelatedByProfileId(List<Integer> projectIds, Integer profileId, Pageable pageable);
}
