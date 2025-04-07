package org.babor.dumo.service;

import lombok.RequiredArgsConstructor;
import org.babor.dumo.dao.IssueDao;
import org.babor.dumo.dao.SprintDao;
import org.babor.dumo.dto.SprintResponseDto;
import org.babor.dumo.entity.Issue;
import org.babor.dumo.entity.Project;
import org.babor.dumo.entity.Sprint;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SprintService {
    private final SprintDao sprintDao;
    private final IssueDao issueDao;
    private final ProjectService projectService;
    private final ModelMapper modelMapper;

    public void save(int projectId, Sprint sprint) {
        Project project = modelMapper.map(projectService.findById(projectId),Project.class);
        sprint.setProject(project);
        sprint.setStartDate(LocalDateTime.now());
        sprint.setIsActive(true);
        sprintDao.save(sprint);
    }

    public List<SprintResponseDto> findAllByProjectId(int projectId) {
        List<Sprint> sprints = sprintDao.findAllByProjectId(projectId);
        return sprints.stream()
                .map(sprint -> modelMapper.map(sprint, SprintResponseDto.class))
                .toList();
    }

    public SprintResponseDto findById(Integer id) {
        return modelMapper.map(sprintDao.findById(id), SprintResponseDto.class);
    }

    public void finish(int sprintId) {
        Sprint sprint = sprintDao.findById(sprintId);
        sprint.setIsActive(false);
        LocalDateTime now = LocalDateTime.now();
        sprint.setEndDate(now);
        sprintDao.save(sprint);
        List<Issue> issues = issueDao.findOpenIssues(sprint);
        for (Issue issue : issues) {
            issue.setSprint(null);
            issueDao.save(issue);
        }
    }

    public Boolean isActive(int sprintId) {
        Sprint sprint = sprintDao.findById(sprintId);
        return sprint != null && sprint.getIsActive();
    }
}
