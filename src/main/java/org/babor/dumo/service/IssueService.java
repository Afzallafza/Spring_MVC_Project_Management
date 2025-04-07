package org.babor.dumo.service;


import lombok.RequiredArgsConstructor;
import org.babor.dumo.dao.IssueDao;
import org.babor.dumo.dto.SprintResponseDto;
import org.babor.dumo.entity.*;
import org.babor.dumo.enums.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueService {
    private final IssueDao issueDao;
    private final UserService userService;
    private final SprintService sprintService;
    private final ModelMapper modelMapper;
    private final ProjectService projectService;

    public List<Issue> findAllBySprintId(int sprintId) {
        return issueDao.findAllBySprintId(sprintId);
    }

    public void save(Issue issue, int userId, Integer sprintId, int projectId) {
        User assignee = userService.findById(userId);
        Sprint sprint = modelMapper.map(sprintService.findById(sprintId), Sprint.class);
        Project project = modelMapper.map(projectService.findById(projectId), Project.class);
        issue.setStatus(IssueStatus.PENDING);
        issue.setProject(project);
        issue.setAssignee(assignee);
        issue.setSprint(sprint);
        issueDao.save(issue);
    }

    public List<Issue> filter(List<String> types, List<String> priorities, List<Integer> sprints) {
        List<Sprint> sprintList = new ArrayList<>();
        for (Integer integer : sprints) {
            Sprint sprint = modelMapper.map(sprintService.findById(integer), Sprint.class);
            sprintList.add(sprint);
        }
        return issueDao.filter(types, priorities, sprintList);
    }

    public void updateStatus(String status, int issueId) {
        issueDao.updateStatus(status, issueId);
    }

    public Issue findById(int issueId) {
        return issueDao.findById(issueId);
    }

    public List<Issue> findBacklogs(int projectId) {
        Project project = modelMapper.map(projectService.findById(projectId), Project.class);
        return issueDao.findBacklogs(project);
    }

    public void moveBacklog(Integer sprintId, int issueId) {
        Issue issue = issueDao.findById(issueId);
        if (sprintId != null) {
            Sprint sprint = modelMapper.map(sprintService.findById(sprintId), Sprint.class);
            issue.setSprint(sprint);
            issueDao.save(issue);
        }
    }

    public int findOpenIssuesCount(int sprintId) {
        Sprint sprint = modelMapper.map(sprintService.findById(sprintId), Sprint.class);
        return issueDao.findOpenIssuesCount(sprint).intValue();
    }

}
