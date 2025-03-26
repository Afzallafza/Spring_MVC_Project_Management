<%--
  Created by IntelliJ IDEA.
  User: afzal
  Date: 3/25/25
  Time: 1:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        form {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
            box-sizing: border-box;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            font-size: 14px;
            color: #333333;
            background-color: #ffffff;
            transition: border-color 0.3s;
            box-sizing: border-box;
        }
        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="password"]:focus {
            border-color: #007bff;
            outline: none;
        }
        h4[name="error"] {
            color: #d9534f;
            font-size: 12px;
            margin: -15px 0 15px;
        }
        button[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            color: #ffffff;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button[type="submit"]:hover {
            background-color: #0056b3;
        }
        small{
            font-size: 12px;
            font-weight: bold;
            margin: 10px;
            text-align: center;
        }
    </style>

    <title>Title</title>
</head>
<body>
<form action="/register" method="post">
    <input type="text" name="name" placeholder="Enter Name"/>
    <h4 name="error">${errors.getFieldError("name").defaultMessage}</h4>
    <input type="text" name="username" placeholder="Enter Username"/>
    <h4 name="error">${errors.getFieldError("username").defaultMessage}</h4>
    <input type="email" name="email" placeholder="Enter email"/>
    <h4 name="error">${errors.getFieldError("email").defaultMessage}</h4>
    <input type="password" name="password" placeholder="Enter Password"/>
    <h4 name="error">${errors.getFieldError("password").defaultMessage}</h4>
    <button type="submit">Register</button>
    <small>New User?
        <a href="/login">Login</a>
    </small>
</form>
</body>
</html>
