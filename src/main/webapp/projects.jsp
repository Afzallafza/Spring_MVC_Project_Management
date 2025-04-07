<%--
  Created by IntelliJ IDEA.
  User: afzal
  Date: 3/31/25
  Time: 3:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

  <title>Title</title>
  <link rel="stylesheet" href="/resources/css/projects.css">
</head>
<body>
<div class="container">
  <h3>Project List</h3>
  <c:forEach var="item" items="${projectList}">
    <a href="/dashboard/${item.id}">
      <h4>${item.name}</h4>
    </a>
  </c:forEach>
</div>
</body>
</html>
