package com.geoffrey.mini_trello_back.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("""
            SELECT COUNT(r) > 0 
            FROM Role r
            WHERE LOWER(r.name) = LOWER(:name)
            """)
    boolean existsByName(String name);

    Optional<Role> findByName(String roleName);
}
