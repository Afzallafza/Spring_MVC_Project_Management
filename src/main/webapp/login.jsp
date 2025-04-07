<%--
  Created by IntelliJ IDEA.
  User: afzal
  Date: 3/29/25
  Time: 4:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/login.css">
</head>
<body>
<form action="/auth/login" method="post">
    <h3>Login</h3>
    <input type="text" name="username" placeholder="Enter Username" required/>
    <input type="password" name="password" placeholder="Enter Password" required/>
    <c:if test="${errorMessage!=null}">
        <small class="error">${errorMessage}</small>
    </c:if>
    <button type="submit">Login</button>
    <small>New user?
        <a href="/auth/register">Register</a>
    </small>
</form>
</body>
</html>
