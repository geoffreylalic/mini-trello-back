package com.geoffrey.mini_trello_back.project;

import com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto;
import com.geoffrey.mini_trello_back.user.dto.ProjectTaskStatsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findProjectsByOwnerId(Integer ProfileId);

    @Query("""
            SELECT new com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto(
                p.id,
                p.name,
                p.description
                )
            FROM Project p
            WHERE p.owner.id = :profileId
            """)
    List<SimpleProjectDto> findProjectIdsByOwnerId(Integer profileId);

    @Query("""
            SELECT DISTINCT t.project.id
            FROM Task t
            WHERE t.assignedTo.id = :profileId
            """)
    List<Integer> findProjectIdsByProfileId(Integer profileId);


    @Query("""
            SELECT DISTINCT t.project.id
            FROM Task t
            WHERE t.id IN :tasksIds
            """)
    List<Integer> findProjectIdsByTaskIds(List<Integer> taskIds);

    @Query("""
            SELECT DISTINCT p
            FROM Project p
            WHERE p.id IN :projectIds OR p.owner.id = :profileId
            ORDER BY p.id ASC
            """)
    Page<Project> findProjectsRelatedByProfileId(List<Integer> projectIds, Integer profileId, Pageable pageable);

    @Query("""
            SELECT new com.geoffrey.mini_trello_back.user.dto.ProjectTaskStatsDto(
                p.id,
                COUNT (t),
                SUM (CASE WHEN t.status = com.geoffrey.mini_trello_back.task.Status.DONE THEN 1 ELSE 0 END)
            )
            FROM Project p
            LEFT JOIN p.tasks t
            WHERE p.id IN :projectIds
            GROUP BY p.id
            """)
    List<ProjectTaskStatsDto> findProjectTasksStatsByIds(List<Integer> projectIds);
}
