package org.babor.dumo.service;

import lombok.RequiredArgsConstructor;
import org.babor.dumo.dao.ProjectDao;
import org.babor.dumo.dto.ProjectRequestDto;
import org.babor.dumo.dto.ProjectResponseDto;
import org.babor.dumo.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectDao projectDao;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public void save(ProjectRequestDto projectRequestDto, List<String> roles, List<Integer> members) {
        Map<User, String> userRoles = new HashMap<>();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < members.size(); i++) {
            User user = userService.findById(members.get(i));
            userRoles.put(user, roles.get(i));
            users.add(user);
        }
        Project project = modelMapper.map(projectRequestDto, Project.class);
        project.setCreationDate(LocalDateTime.now());
        project.setMembers(users);
        projectDao.save(project);
        projectDao.addUserProjectAssociation(project, userRoles);
    }

    public List<ProjectResponseDto> findProjectsByUser(String username) {
        List<Project> projects = projectDao.findProjectsByUser(username);
        return projects.stream()
                .map(project -> modelMapper.map(project, ProjectResponseDto.class))
                .toList();

    }

    public ProjectResponseDto findById(int id) {
        return modelMapper.map(projectDao.findById(id), ProjectResponseDto.class);
    }
}
