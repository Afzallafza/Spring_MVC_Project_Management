package org.babor.dumo.validation;

import lombok.RequiredArgsConstructor;
import org.babor.dumo.service.SprintService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SprintValidator {
    private final SprintService sprintService;

    public void isActive(int sprintId) {
        if(!sprintService.isActive(sprintId)){
            throw new IllegalArgumentException("No active sprint with id " + sprintId);
        }
    }
}
