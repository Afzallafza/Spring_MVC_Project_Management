package org.babor.dumo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.babor.dumo.entity.User;
import org.babor.dumo.enums.IssuePriority;
import org.babor.dumo.enums.IssueStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubtaskResponseDto {
    private String name;
    private String description;
    private IssueStatus status;
    private IssuePriority priority;
    private User assignee;
}
