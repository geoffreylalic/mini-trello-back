package com.geoffrey.mini_trello_back.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public int countUsersByEmail(String email);

    public Optional<User> findUserById(int id);
}
