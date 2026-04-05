<%--
  Created by IntelliJ IDEA.
  User: lukss
  Date: 6/5/2025
  Time: 10:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
</head>
<body>
<h1>The name <%= request.getParameter("name")%> is already in use</h1>
<br/>
<p>Please enter another name and password.</p>

<form action="accCreateServlet" method="post">
    User Name: <input type="text" name="name"/> <br/>
    Password: <input type="text" name="password"/>
    <input type="submit" value="login"/>
</form>
</body>
</html>
