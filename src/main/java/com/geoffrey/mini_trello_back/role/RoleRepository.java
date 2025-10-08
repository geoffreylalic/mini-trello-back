package com.geoffrey.mini_trello_back.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("""
            SELECT 1 
            FROM Role r
            WHERE LOWER(r.name) = LOWER(:name)
            """)
    boolean existsByName(String name);
}
