package org.babor.dumo.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.babor.dumo.Constants;
import org.babor.dumo.dto.ProjectRequestDto;
import org.babor.dumo.service.ProjectService;
import org.babor.dumo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping
    public String getAll(HttpSession session, Model model) {
        model.addAttribute(Constants.PROJECT_LIST, projectService.findProjectsByUser(session.getAttribute(Constants.USERNAME).toString()));
        return "projects";
    }

    @GetMapping("/create-project")
    public String create(Model model) {
        model.addAttribute(Constants.MEMBERS, userService.findAll());
        return "newProjectInfo";
    }

    @PostMapping("/create-project")
    public String create(@Valid @ModelAttribute(Constants.PROJECT) ProjectRequestDto projectRequestDto,
                                @RequestParam(Constants.ROLE) List<String> roles,
                                @RequestParam(Constants.INCLUDE) List<Integer> members) {
        projectService.save(projectRequestDto, roles, members);
        return "redirect:/";
    }
}
