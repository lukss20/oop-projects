<%@ page import="com.example.demo1.DataManager" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Students Store</title>
</head>
<body>
<h1>Students Store</h1>
<p>Items available:</p>

  <%
    DataManager dm = (DataManager) application.getAttribute("datamanager");
    for(DataManager.item p: dm.getItems(null)) {
      out.print("<a href=\"item.jsp?id=" + p.id + "\">" + p.name + "</a><br>");

    }

  %>

</body>
</html>