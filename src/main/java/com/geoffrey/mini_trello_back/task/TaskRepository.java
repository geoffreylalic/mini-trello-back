package com.geoffrey.mini_trello_back.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findTasksByProjectId(Integer projectId);

    Page<Task> findTasksByProjectId(Integer projectId, Pageable pageable);

    @Query("""
            SELECT t
            FROM Task t
            WHERE t.assignedTo.id = :profileId
            """)
    Page<Task> findTasksByProfileId(Integer profileId, Pageable pageable);

    List<Task> findTasksByAssignedToId(Integer projectId);

    Page<Task> findTasksByAssignedToId(Integer projectId, Pageable pageable);

}
