<%--
  Created by IntelliJ IDEA.
  User: afzal
  Date: 3/29/25
  Time: 8:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/newProjectInfo.css">
</head>
<body>
<div class="container">
    <h3>Project Info</h3>
    <form action="/project/create-project" method="post">
        <input type="text" name="name" placeholder="Project Name" required>
        <textarea name="description" cols="30" rows="2" placeholder="Enter Description" required></textarea>
        <h3>Add Members</h3>
        <table class="table">
            <tr>
                <th>Name</th>
                <th>Role</th>
                <th>Include</th>
            </tr>
            <c:forEach var="item" items="${members}">
                <tr>
                    <td>${item.name}</td>
                    <td>
                        <select name="role" >
                            <option selected disabled>Choose Role</option>
                            <option value="Project_Manager">Project Manager</option>
                            <option value="Team_Lead">Team Lead</option>
                            <option value="Developer">Developer</option>
                            <option value="QA">QA</option>
                        </select>
                    </td>
                    <td>
                        <input type="checkbox" name="include" value="${item.id}" >
                    </td>
                </tr>
            </c:forEach>
        </table>
        <button>Create</button>
    </form>

</div>
</body>
</html>
