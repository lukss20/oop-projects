<%@ page import="com.example.demo1.DataManager" %>
<%@ page import="java.util.HashSet" %><%--
  Created by IntelliJ IDEA.
  User: lukss
  Date: 6/7/2025
  Time: 6:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  DataManager dm = (DataManager) application.getAttribute("datamanager");
  HashSet<String> temp = new HashSet<>();
  temp.add(request.getParameter("id"));
  DataManager.item item = dm.getItems(temp).get(0);
%>
<html>
<head>
    <title><%=item.name%></title>
</head>
<body>
<h1><%= item.name %></h1>
<img src="<%="store-images/"+item.image%>">
<form action="ShoppingCartServlet" method="post">
  $<%=item.price%> <input name="itemID" type="hidden" value="<%= item.id %>"/>
  <input type="submit" value="Add to cart"/>
</form>

</body>
</html>
