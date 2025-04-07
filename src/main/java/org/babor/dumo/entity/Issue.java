package org.babor.dumo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.babor.dumo.enums.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name can not be empty")
    private String name;
    @NotNull(message = "Description can not be empty")
    private String description;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type must have a value")
    private IssueType type;
    @Enumerated(EnumType.STRING)
    private IssueStatus status;
    @NotNull(message = "Priority must have a value")
    @Enumerated(EnumType.STRING)
    private IssuePriority priority;
    @ManyToOne
    private Sprint sprint;
    @ManyToOne
    private User assignee;
    @OneToMany(mappedBy = "issue", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Subtask> subtasks;
    @ManyToOne
    private Project project;
    public Issue(String name, String description, IssueType type, IssueStatus status, IssuePriority priority, Sprint sprint, User user) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.status = status;
        this.priority = priority;
        this.sprint = sprint;
        this.assignee = user;
    }

}
