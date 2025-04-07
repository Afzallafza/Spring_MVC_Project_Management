package org.babor.dumo.validation;


import lombok.RequiredArgsConstructor;
import org.babor.dumo.service.ProjectService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectValidator {
    private final ProjectService projectService;

    public void isPresent(int projectId) {
        if (projectService.findById(projectId) == null) {
            throw new IllegalArgumentException("Project with id " + projectId + " does not exist");
        }
    }
}
