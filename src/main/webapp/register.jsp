<%--
  Created by IntelliJ IDEA.
  User: afzal
  Date: 3/29/25
  Time: 4:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/register.css">
</head>
<body>
<form action="/auth/register" method="post">
    <h3>Register</h3>
    <input type="text" name="name" placeholder="Enter Name" required>
    <input type="text" name="username" placeholder="Enter Username" required>
    <input type="email" name="email" placeholder="Enter Email" required>
    <input type="password" name="password" placeholder="Enter Password" required>
    <button type="submit">Register</button>
    <small>
        <a href="/auth/login">Login</a>
    </small>
</form>

</body>
</html>
