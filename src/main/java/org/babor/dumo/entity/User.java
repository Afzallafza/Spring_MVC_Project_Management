package org.babor.dumo.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String name;
    private String password;
    private String email;
    private String role;
    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private List<Project> projects;
    @OneToMany(mappedBy = "assignee")
    private List<Issue> issues;
    public User(String username, String name, String password, String email) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
