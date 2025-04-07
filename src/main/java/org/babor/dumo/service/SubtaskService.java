package org.babor.dumo.service;

import lombok.RequiredArgsConstructor;
import org.babor.dumo.dao.SubtaskDao;
import org.babor.dumo.dto.SubtaskResponseDto;
import org.babor.dumo.entity.Issue;
import org.babor.dumo.entity.Subtask;
import org.babor.dumo.entity.User;
import org.babor.dumo.enums.IssueStatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubtaskService {
    private final SubtaskDao subtaskDao;
    private final UserService userService;
    private final IssueService issueService;
    private final ModelMapper modelMapper;

    public List<SubtaskResponseDto> findAllByIssueId(int issueId) {
        return subtaskDao.findAllByIssueId(issueId).stream()
                .map(subtask -> modelMapper.map(subtask, SubtaskResponseDto.class))
                .toList();
    }

    public void save(Subtask subtask, int issueId, int assigneeId, String reporterUsername) {
        User reporter = userService.findByUsername(reporterUsername);
        User assignee = userService.findById(assigneeId);
        Issue issue = issueService.findById(issueId);
        subtask.setReporter(reporter);
        subtask.setAssignee(assignee);
        subtask.setStatus(IssueStatus.PENDING);
        subtask.setIssue(issue);
        subtaskDao.save(subtask);
    }

}
