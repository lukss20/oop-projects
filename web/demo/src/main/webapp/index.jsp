<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Welcome</title>
</head>
<body>
<h1><%= "Welcome to Homework 5" %></h1>
<br/>
<p>Please log in.</p>

<form action="accLoginServlet" method="post">
  User Name: <input type="text" name="name"/> <br/>
  Password: <input type="text" name="password"/>
  <input type="submit" value="login"/>
</form>
<a href="createNewAccount.jsp">Create Account</a>
</body>
</html>