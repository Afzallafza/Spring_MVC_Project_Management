package org.babor.dumo.validation;


import lombok.RequiredArgsConstructor;
import org.babor.dumo.enums.IssueStatus;
import org.babor.dumo.service.IssueService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IssueValidator {
    private final IssueService issueService;

    public void isPresent(int issueId) {
        if (issueService.findById(issueId) == null) {
            throw new IllegalArgumentException("Issue with id " + issueId + " does not exist");
        }
    }

    public void validStatus(String newStatus) {
        try {
            IssueStatus.valueOf(newStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status " + newStatus);
        }
    }
    public void isEmpty(List<?> list) {
        if(list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List Cannot be null or empty");
        }
    }
}
