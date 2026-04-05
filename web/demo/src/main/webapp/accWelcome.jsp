<%--
  Created by IntelliJ IDEA.
  User: lukss
  Date: 6/5/2025
  Time: 10:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome <%= request.getParameter("name") %></title>
</head>
<body>
<h1>Welcome <%= request.getParameter("name") %></h1>
</body>
</html>
