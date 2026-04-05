<%--
  Created by IntelliJ IDEA.
  User: lukss
  Date: 6/5/2025
  Time: 10:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Information Incorrect</title>
</head>
<body>
<h1><%= "Please try Again" %></h1>
<br/>
<p>either you username or password is incorrect. please try again</p>

<form action="accLoginServlet" method="post">
    User Name: <input type="text" name="name"/> <br/>
    Password: <input type="text" name="password"/>
    <input type="submit" value="login"/>
</form>
<a href="createNewAccount.jsp">Create Account</a>
</body>
</html>
