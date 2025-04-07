<%--
  Created by IntelliJ IDEA.
  User: afzal
  Date: 4/3/25
  Time: 10:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/finishSprint.css">
</head>
<body>
<div class="container">
    <h2>Complete ${sprint.name}</h2>
    <c:if test="${openIssues>0}">
        <h3>You have currently ${openIssues} open issues.</h3>
        <p>Do you want to complete this sprint by moving the open issues to the backlog?</p>
    </c:if>
    <button class="cancel" onclick="window.location.href='/dashboard/${projectId}/sprints'">Cancel</button>
    <form action="/dashboard/${projectId}/sprints/sprint/finish-sprint" method="post">
        <button class="finish" type="submit" name="finish" value="${sprint.id}">Finish</button>
    </form>

</div>
</body>
</html>
