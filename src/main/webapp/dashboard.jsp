<%--
  Created by IntelliJ IDEA.
  User: afzal
  Date: 3/31/25
  Time: 3:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/dashboard.css">
</head>
<body>
<div class="navbarForm">
    <form action="/dashboard/${projectId}/navigate" method="post">
        <button name="dashboardMenu" type="submit" value="backlog"
                class="${sessionScope.dashboardMenu.equals('backlog')?'active-btn':''}">Backlog
        </button>
        <button name="dashboardMenu" type="submit" value="board"
                class="${sessionScope.dashboardMenu.equals('board')?'active-btn':''}">Board
        </button>
        <button name="dashboardMenu" type="submit" value="sprints"
                class="${sessionScope.dashboardMenu.equals('sprints')?'active-btn':''}">Sprints
        </button>
        <button name="dashboardMenu" type="submit" value="issues"
                class="${sessionScope.dashboardMenu.equals('issues')?'active-btn':''}">Issues
        </button>
    </form>
</div>
<div class="container">
    <c:if test="${sessionScope.dashboardMenu.equals('sprints')}">
        <div class="sprints">
            <div class="sprintForm">
                <form method="post" action="/dashboard/${projectId}/sprints/create-sprint">
                    <h3>Create Sprint</h3>
                    <input type="text" name="name" placeholder="Name" required/>
                    <select name="duration" required>
                        <option selected disabled>Choose Duration</option>
                        <option value="1">1 Week</option>
                        <option value="2">2 Week</option>
                        <option value="3">3 Week</option>
                        <option value="4">4 Week</option>
                    </select>
                    <textarea name="goal" cols="30" rows="5" placeholder="What do you want to achieve..."
                              required></textarea>
                    <button type="submit">Create</button>
                </form>
            </div>
            <h3>Sprints </h3>
            <form action="/dashboard/${projectId}/sprints/sprint" method="post">
                <c:forEach var="item" items="${sprints}">
                    <c:choose>
                        <c:when test="${sessionScope.activeSprint.id==item.id}">
                            <button value="${item.id}" type="submit" class="sprint" name="sprintId"
                                    style="background: rgba(100,149,237,0.37);border:1px solid cornflowerblue"
                            >${item.name}</button>
                        </c:when>
                        <c:otherwise>
                            <button value="${item.id}" type="submit" class="sprint"
                                    name="sprintId">${item.name}</button>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form>
        </div>
        <c:if test="${sessionScope.activeSprint!=null}">
            <div class="viewSprint">
                <div class="sprintInfo">
                    <h2>${sessionScope.activeSprint.name}</h2>
                    <form action="/dashboard/${projectId}/sprints/sprint/open-issues" method="post">
                        <button type="submit" name="sprintId" value="${sessionScope.activeSprint.id}">Finish</button>
                    </form>
                </div>
                <table>
                    <tr>
                        <th>Issue Type</th>
                        <th>Name</th>
                        <th>Priority</th>
                        <th>Status</th>
                        <th>Assignee</th>
                    </tr>
                    <c:if test="${issues!=null}">
                        <c:forEach var="issue" items="${issues}">
                            <tr>
                                <td>${issue.type}</td>
                                <td>${issue.name}</td>
                                <td>${issue.priority}</td>
                                <td>${issue.status}</td>
                                <td>${issue.assignee.name}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </table>
                <form action="/dashboard/${projectId}/sprints/sprint/create-issue" method="post" class="createIssue">
                    <select name="type" required>
                        <option selected disabled>Select Type</option>
                        <option value="BUG">Bug</option>
                        <option value="STORY">Story</option>
                        <option value="TASK">Task</option>
                    </select>
                    <input type="text" name="name" placeholder="Name" required/>
                    <textarea name="description" cols="30" rows="1" required
                              placeholder="Describe..."></textarea>
                    <select name="priority" required>
                        <option selected disabled>Choose Priority</option>
                        <option value="LOW">Low</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="HIGH">High</option>
                    </select>
                    <select name="user" required>
                        <option selected disabled>Select Assignee</option>
                        <c:forEach var="item" items="${sessionScope.assigneeList}">
                            <option value="${item.id}">${item.name}</option>
                        </c:forEach>
                    </select>
                    <button type="submit" name="sprintId" value="${sessionScope.activeSprint.id}">Create Issue</button>
                </form>
            </div>
        </c:if>
    </c:if>
    <c:if test="${sessionScope.dashboardMenu.equals('backlog')}">
        <div class="backlog">
            <h1>Backlogs</h1>
            <div class="viewSprint">
                <table>
                    <tr>
                        <th>Issue Type</th>
                        <th>Name</th>
                        <th>Priority</th>
                        <th>Status</th>
                        <th>Assignee</th>
                        <th>Move to</th>
                    </tr>
                    <c:forEach var="issue" items="${backlogs}">
                        <tr>
                            <td>${issue.type}</td>
                            <td>${issue.name}</td>
                            <td>${issue.priority}</td>
                            <td>${issue.status}</td>
                            <td>${issue.assignee.name}</td>
                            <td class="pk">
                                <form action="/dashboard/${projectId}/backlog/move-backlog" method="post">
                                    <select name="selectedSprint" required>
                                        <option selected disabled>Select Sprint</option>
                                        <c:forEach var="item" items="${activeSprints}">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </select>
                                    <button type="submit" name="issueId" value="${issue.id}">Move</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <form action="/dashboard/${projectId}/sprints/sprint/create-issue" method="post" class="createIssue">
                    <select name="type" required>
                        <option selected disabled>Select Type</option>
                        <option value="BUG">Bug</option>
                        <option value="STORY">Story</option>
                        <option value="TASK">Task</option>
                    </select>
                    <input type="text" name="name" placeholder="Name" required/>
                    <textarea name="description" cols="30" rows="1" required
                              placeholder="Describe..."></textarea>
                    <select name="priority" required>
                        <option selected disabled>Choose Priority</option>
                        <option value="LOW">Low</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="HIGH">High</option>
                    </select>
                    <select name="user" required>
                        <option selected disabled>Select Assignee</option>
                        <c:forEach var="item" items="${sessionScope.assigneeList}">
                            <option value="${item.id}">${item.name}</option>
                        </c:forEach>
                    </select>
                    <button type="submit">Create Issue</button>
                </form>
            </div>
        </div>

    </c:if>
    <c:if test="${sessionScope.dashboardMenu.equals('board')}">
        <div class="conn">
            <div class="leftBoard">
                <div class="board">
                    <h1>board</h1>
                    <form action="/dashboard/${projectId}/board/filter" method="post" class="boardFilter">
                        <div class="issue">
                            <h4>Select Issues</h4>
                            <div class="con">
                                <input type="checkbox" name="selectedType" value="BUG">
                                <label>Bug</label>
                            </div>
                            <div class="con">
                                <input type="checkbox" name="selectedType" value="STORY">
                                <label>Story</label>
                            </div>
                            <div class="con">
                                <input type="checkbox" name="selectedType" value="TASK">
                                <label>Task</label>
                            </div>
                        </div>
                        <div class="priority">
                            <h4>Select Priorities</h4>
                            <div class="con">
                                <input type="checkbox" name="selectedPriority" value="LOW">
                                <label>Low</label>
                            </div>
                            <div class="con">
                                <input type="checkbox" name="selectedPriority" value="MEDIUM">
                                <label>Medium</label>
                            </div>
                            <div class="con">
                                <input type="checkbox" name="selectedPriority" value="HIGH">
                                <label>High</label>
                            </div>
                        </div>
                        <div class="selectSprints">
                            <h4>Select Sprints</h4>
                            <c:forEach var="item" items="${sprints}">
                                <div class="con">
                                    <input type="checkbox" value="${item.id}" name="selectedSprint">
                                    <label>${item.name}</label>
                                </div>
                            </c:forEach>
                        </div>
                        <button type="submit">Filter</button>
                    </form>
                    <div class="cor">
                        <div class="pending">
                            <h4>Pending</h4>
                            <c:forEach var="item" items="${sessionScope.filteredIssues}">
                                <c:if test="${item.status=='PENDING'}">
                                    <div class="ind">
                                        <form action="/dashboard/${projectId}/board/view-issue" method="post"
                                              class="viewIssue">
                                            <button type="submit" value="${item.id}" name="issueId">
                                                <small>${item.type}</small>
                                                <small>${item.name}</small>
                                                <small>Assignee: ${item.assignee.name}</small>
                                                <small>${item.sprint.name}</small>
                                            </button>
                                        </form>
                                        <form method="post" action="/dashboard/${projectId}/board/update-status">
                                            <select name="newStatus" required>
                                                <option selected disabled>Choose New Status</option>
                                                <c:if test="${item.status!='PENDING'}">
                                                    <option value="PENDING">Pending</option>
                                                </c:if>
                                                <c:if test="${item.status!='IN_PROGRESS'}">
                                                    <option value="IN_PROGRESS">In-Progress</option>
                                                </c:if>
                                                <c:if test="${item.status!='COMPLETED'}">
                                                    <option value="COMPLETED">Completed</option>
                                                </c:if>
                                            </select>
                                            <button type="submit" name="issueId" value="${item.id}">Change Status
                                            </button>
                                        </form>
                                    </div>
                                </c:if>
                            </c:forEach>

                        </div>
                        <div class="inProgress">
                            <h4>In-Progress</h4>
                            <c:forEach var="item" items="${sessionScope.filteredIssues}">
                                <c:if test="${item.status=='IN_PROGRESS'}">
                                    <div class="ind">
                                        <form action="/dashboard/${projectId}/board/view-issue" method="post"
                                              class="viewIssue">
                                            <button type="submit" value="${item.id}" name="issueId">
                                                <small>${item.type}</small>
                                                <small>${item.name}</small>
                                                <small>Assignee: ${item.assignee.name}</small>
                                                <small>${item.sprint.name}</small>
                                            </button>
                                        </form>
                                        <form method="post" action="/dashboard/${projectId}/board/update-status">
                                            <select name="newStatus" required>
                                                <option selected disabled>Choose New Status</option>
                                                <c:if test="${item.status!='PENDING'}">
                                                    <option value="PENDING">Pending</option>
                                                </c:if>
                                                <c:if test="${item.status!='IN_PROGRESS'}">
                                                    <option value="IN_PROGRESS">In-Progress</option>
                                                </c:if>
                                                <c:if test="${item.status!='COMPLETED'}">
                                                    <option value="COMPLETED">Completed</option>
                                                </c:if>
                                            </select>
                                            <button type="submit" name="issueId" value="${item.id}">Change Status
                                            </button>
                                        </form>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                        <div class="done">
                            <h4>Completed</h4>
                            <c:forEach var="item" items="${sessionScope.filteredIssues}">
                                <c:if test="${item.status=='COMPLETED'}">
                                    <div class="ind">
                                        <form action="/dashboard/${projectId}/board/view-issue" method="post"
                                              class="viewIssue">
                                            <button type="submit" value="${item.id}" name="issueId">
                                                <small>${item.type}</small>
                                                <small>${item.name}</small>
                                                <small>Assignee: ${item.assignee.name}</small>
                                                <small>${item.sprint.name}</small>
                                            </button>
                                        </form>

                                        <form method="post" action="/dashboard/${projectId}/board/update-status">
                                            <select name="newStatus" required>
                                                <option selected disabled>Choose New Status</option>
                                                <c:if test="${item.status!='PENDING'}">
                                                    <option value="PENDING">Pending</option>
                                                </c:if>
                                                <c:if test="${item.status!='IN_PROGRESS'}">
                                                    <option value="IN_PROGRESS">In-Progress</option>
                                                </c:if>
                                                <c:if test="${item.status!='COMPLETED'}">
                                                    <option value="COMPLETED">Completed</option>
                                                </c:if>
                                            </select>
                                            <button type="submit" name="issueId" value="${item.id}">Change Status
                                            </button>
                                        </form>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <c:if test="${issue!=null}">
                <div class="rightBoard">
                    <div class="indIssue">
                        <small>${issue.type}</small>
                        <h2>${issue.name}</h2>
                        <p>${issue.description}</p>
                        <h5>${issue.priority}</h5>
                        <h5>${issue.assignee.name}</h5>
                    </div>
                    <form action="/dashboard/${projectId}/board/create-subtask" method="post" class="creatIssue">
                        <h4>Create Subtask</h4>
                        <input type="text" name="name" placeholder="Name" required/>
                        <textarea name="description" cols="10" rows="1"
                                  placeholder="Describe..."></textarea>
                        <select name="priority" required>
                            <option selected disabled>Choose Priority</option>
                            <option value="LOW">Low</option>
                            <option value="MEDIUM">Medium</option>
                            <option value="HIGH">High</option>
                        </select>
                        <select name="subtaskAssignee" required>
                            <option selected disabled>Select Assignee</option>
                            <c:forEach var="item" items="${sessionScope.assigneeList}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        <button type="submit" name="issueId" value="${issue.id}">Create Issue</button>
                    </form>
                    <h4>Subtasks of ${issue.name}</h4>
                    <table>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Status</th>
                            <th>Priority</th>
                            <th>Assignee</th>
                        </tr>
                        <c:forEach var="item" items="${subtaskList}">
                            <tr>
                                <td>${item.name}</td>
                                <td>${item.description}</td>
                                <td>${item.status}</td>
                                <td>${item.priority}</td>
                                <td>${item.assignee.name}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
        </div>
    </c:if>

</div>

</body>
</html>
