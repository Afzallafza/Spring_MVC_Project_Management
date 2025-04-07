package org.babor.dumo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Project project;
    @ManyToOne
    private User member;
    private String role;

    public UserProject(Project project, User user, String role) {
        this.project = project;
        this.member = user;
        this.role = role;
    }

}
