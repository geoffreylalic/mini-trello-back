package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    int countByUser_Id(Integer userId);

    @Query("SELECT DISTINCT p " +
            "FROM Project p " +
            "LEFT JOIN p.tasks t " +
            "WHERE p.owner.id = :profileId OR t.assignedTo.id = :profileId")
    List<Project> findRelatedProjects(Integer profileId);
}
