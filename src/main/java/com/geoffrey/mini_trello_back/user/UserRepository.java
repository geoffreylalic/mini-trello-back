package com.geoffrey.mini_trello_back.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public int countUsersByEmail(String email);
}
