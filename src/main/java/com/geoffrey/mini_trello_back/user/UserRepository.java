package com.geoffrey.mini_trello_back.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    int countUsersByEmail(String email);

    Optional<User> findUserById(int id);
}
