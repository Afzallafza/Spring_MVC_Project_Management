package org.babor.dumo.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> members;
    @OneToMany(mappedBy = "project")
    private List<Sprint> sprints;
    @OneToMany(mappedBy = "project")
    private List<Issue> issues;

    public Project(String name, String description, LocalDateTime creationDate, Boolean isActive, List<User> members) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.isActive = isActive;
        this.members = members;
    }
}
