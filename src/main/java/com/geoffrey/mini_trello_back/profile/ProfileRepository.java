package com.geoffrey.mini_trello_back.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    int countByUser_Id(Integer userId);
}
