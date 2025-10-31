package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.project.Project;
import com.geoffrey.mini_trello_back.user.dto.ProfileProjectMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    int countByUser_Id(Integer userId);

    @Query("SELECT DISTINCT p " +
            "FROM Project p " +
            "LEFT JOIN p.tasks t " +
            "WHERE p.owner.id = :profileId OR t.assignedTo.id = :profileId")
    List<Project> findRelatedProjects(Integer profileId);

    @Query("SELECT DISTINCT p " +
            "FROM Project p " +
            "LEFT JOIN p.tasks t " +
            "WHERE p.owner.id = :profileId OR t.assignedTo.id = :profileId")
    Page<Project> findRelatedProjects(Integer profileId, Pageable pageable);

    @Query("SELECT DISTINCT prof " +
            "FROM Task t " +
            "LEFT JOIN t.assignedTo prof " +
            "LEFT JOIN t.project proj " +
            "WHERE proj.id = :projectId AND prof IS NOT NULL")
    List<Profile> findProfilesByRelatedProject(Integer projectId);

    @Query("SELECT DISTINCT prof " +
            "FROM Task t " +
            "LEFT JOIN t.assignedTo prof " +
            "LEFT JOIN t.project proj " +
            "WHERE proj.id = :projectId AND prof IS NOT NULL")
    Page<Profile> findProfilesByRelatedProject(Integer projectId, Pageable pageable);

    @Query("""
                SELECT DISTINCT new com.geoffrey.mini_trello_back.user.dto.ProfileProjectMember(proj.id, prof)
                FROM Task t
                LEFT JOIN t.assignedTo prof
                LEFT JOIN t.project proj
                WHERE proj.id in :projectIds AND prof IS NOT NULL
            """)
    List<ProfileProjectMember> findProfilesByRelatedProjectIds(List<Integer> projectIds);

    Optional<Profile> findByUserId(Integer id);
}
