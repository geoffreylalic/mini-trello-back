package com.geoffrey.mini_trello_back.role;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.geoffrey.mini_trello_back.common.BaseEntity;
import com.geoffrey.mini_trello_back.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role")
    @JsonManagedReference
    private List<User> users;


    public Role(String name) {
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
