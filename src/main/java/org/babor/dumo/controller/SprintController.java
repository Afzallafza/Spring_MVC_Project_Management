package org.babor.dumo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.babor.dumo.Constants;
import org.babor.dumo.dto.SprintResponseDto;
import org.babor.dumo.entity.*;
import org.babor.dumo.service.*;
import org.babor.dumo.validation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes({Constants.ASSIGNEE_LIST, Constants.FILTERED_ISSUES,})
@RequestMapping("/dashboard/{projectId}")
public class SprintController {

    private final SprintService sprintService;
    private final IssueService issueService;
    private final UserService userService;
    private final SubtaskService subtaskService;
    private final SprintValidator sprintValidator;
    private final ProjectValidator projectValidator;
    private final IssueValidator issueValidator;
    private final UserValidator userValidator;

    @GetMapping
    public String showDashboard(HttpSession session, Model model,
                                @PathVariable(Constants.PROJECT_ID) int projectId) {
        model.addAttribute(Constants.ASSIGNEE_LIST, userService.findUserByProjectId(projectId));
        if (session.getAttribute(Constants.DASHBOARD_MENU) == null) {
            session.setAttribute(Constants.DASHBOARD_MENU, Constants.SPRINTS);
        }
        return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + Constants.SPRINTS;
    }

    @PostMapping("/sprints/create-sprint")
    public String createSprint(@Valid @ModelAttribute Sprint sprint,
                               @PathVariable(Constants.PROJECT_ID) int projectId) {
        sprintService.save(projectId, sprint);
        return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + Constants.SPRINTS;
    }

    @PostMapping("/navigate")
    public String navigate(@RequestParam(Constants.DASHBOARD_MENU) String value,
                           @PathVariable(Constants.PROJECT_ID) int projectId,
                           HttpSession session) {
        session.setAttribute(Constants.DASHBOARD_MENU, value);
        return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + value;
    }


    @GetMapping("/sprints")
    public String sprints(HttpSession session,
                          @PathVariable(Constants.PROJECT_ID) int projectId, Model model) {
        if (session.getAttribute(Constants.ACTIVE_SPRINT) != null) {
            SprintResponseDto sprint=(SprintResponseDto) session.getAttribute(Constants.ACTIVE_SPRINT);
            model.addAttribute(Constants.ISSUES, issueService.findAllBySprintId(sprint.getId()));
        }
        model.addAttribute(Constants.SPRINTS, sprintService.findAllByProjectId(projectId));
        return Constants.DASHBOARD;
    }

    @PostMapping("/sprints/sprint")
    public String viewSprint(@PathVariable(Constants.PROJECT_ID) int projectId,
                             @RequestParam(Constants.SPRINT_ID) int sprintId,
                             HttpSession session
    ) {
        sprintValidator.isActive(sprintId);
        session.setAttribute(Constants.ACTIVE_SPRINT, sprintService.findById(sprintId));
        return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + Constants.SPRINTS;
    }

    @PostMapping("/sprints/sprint/create-issue")
    public String createIssue(@PathVariable(Constants.PROJECT_ID) int projectId,
                              @Valid @ModelAttribute(Constants.ISSUE) Issue issue,
                              @RequestParam(Constants.USER) int userId,
                              @RequestParam(value = Constants.SPRINT_ID, required = false) Integer sprintId
    ) {
        issueService.save(issue, userId, sprintId, projectId);
        if (sprintId != null) {
            return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + Constants.SPRINTS;
        } else {
            return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + Constants.BACKLOG;
        }
    }

    @PostMapping("/sprints/sprint/open-issues")
    public String getOpenIssues(@PathVariable(Constants.PROJECT_ID) int projectId,
                                @RequestParam(Constants.SPRINT_ID) int sprintId,
                                RedirectAttributes redirectAttributes) {
        sprintValidator.isActive(sprintId);
        redirectAttributes.addFlashAttribute(Constants.OPEN_ISSUES, issueService.findOpenIssuesCount(sprintId));
        redirectAttributes.addFlashAttribute(Constants.SPRINT, sprintService.findById(sprintId));
        return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + "sprints/sprint/finish-sprint";
    }

    @GetMapping("/sprints/sprint/finish-sprint")
    public String validateFinish() {
        return "finishSprint";
    }

    @PostMapping("/sprints/sprint/finish-sprint")
    public String finishSprint(@PathVariable(Constants.PROJECT_ID) int projectId,
                               @RequestParam(Constants.FINISH) int sprintId
    ) {
        sprintValidator.isActive(sprintId);
        sprintService.finish(sprintId);
        return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + Constants.SPRINTS;
    }

    @GetMapping("/board")
    public String board(@PathVariable(Constants.PROJECT_ID) int projectId, Model model) {
        model.addAttribute(Constants.SPRINTS, sprintService.findAllByProjectId(projectId));
        return "dashboard";
    }

    @PostMapping("/board/filter")
    public String filterIssue(@RequestParam(Constants.SELECTED_TYPE) List<String> types,
                              @RequestParam(Constants.SELECTED_PRIORITY) List<String> priorities,
                              @RequestParam(Constants.SELECTED_SPRINT) List<Integer> sprints,
                              @PathVariable(Constants.PROJECT_ID) int projectId,
                              Model model) {
        issueValidator.isEmpty(types);
        issueValidator.isEmpty(priorities);
        issueValidator.isEmpty(sprints);
        model.addAttribute(Constants.FILTERED_ISSUES, issueService.filter(types, priorities, sprints));
        return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + Constants.BOARD;
    }

    @PostMapping("/board/update-status")
    public String updateStatus(@PathVariable(Constants.PROJECT_ID) int projectId,
                               @RequestParam(Constants.ISSUE_ID) int issueId,
                               @RequestParam(Constants.NEW_STATUS) String newStatus) {
        issueValidator.isPresent(issueId);
        issueValidator.validStatus(newStatus);
        issueService.updateStatus(newStatus, issueId);
        return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + Constants.BOARD;
    }

    @PostMapping("/board/view-issue")
    public String viewIssue(@PathVariable(Constants.PROJECT_ID) int projectId,
                            @RequestParam(Constants.ISSUE_ID) int issueId,
                            RedirectAttributes redirectAttributes
    ) {
        issueValidator.isPresent(issueId);
        redirectAttributes.addFlashAttribute(Constants.ISSUE, issueService.findById(issueId));
        redirectAttributes.addFlashAttribute(Constants.SUBTASK_LIST, subtaskService.findAllByIssueId(issueId));
        return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + Constants.BOARD;
    }

    @PostMapping("/board/create-subtask")
    public String createSubtask(@PathVariable(Constants.PROJECT_ID) int projectId,
                                @Valid @ModelAttribute(Constants.SUBTASK) Subtask subtask,
                                @RequestParam(Constants.SUBTASK_ASSIGNEE) int assignee,
                                @RequestParam(Constants.ISSUE_ID) int issueId,
                                HttpSession session
    ) {
        issueValidator.isPresent(issueId);
        userValidator.isPresent(assignee);
        subtaskService.save(subtask, issueId, assignee, session.getAttribute(Constants.USERNAME).toString());
        return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + Constants.BOARD;
    }

    @GetMapping("/backlog")
    public String backlog(@PathVariable(Constants.PROJECT_ID) int projectId, Model model) {
        model.addAttribute(Constants.BACKLOGS, issueService.findBacklogs(projectId));
        model.addAttribute(Constants.ACTIVE_SPRINTS, sprintService.findAllByProjectId(projectId));
        return "dashboard";
    }

    @PostMapping("/backlog/move-backlog")
    public String moveBacklog(@PathVariable(Constants.PROJECT_ID) int projectId,
                              @RequestParam(Constants.SELECTED_SPRINT) Integer sprintId,
                              @RequestParam(Constants.ISSUE_ID) int issueId) {
        sprintValidator.isActive(sprintId);
        issueValidator.isPresent(issueId);
        issueService.moveBacklog(sprintId, issueId);
        return Constants.REDIRECT_TO_DASHBOARD + projectId + "/" + Constants.BACKLOG;
    }

    @ModelAttribute(Constants.PROJECT)
    public int addProjectId(@PathVariable(Constants.PROJECT_ID) Integer projectId) {
        projectValidator.isPresent(projectId);
        return projectId;
    }
}
