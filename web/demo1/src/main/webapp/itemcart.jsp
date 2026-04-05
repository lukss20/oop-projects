<%@ page import="com.example.demo1.DataManager" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.example.demo1.ShoppingCart" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashSet" %><%--
  Created by IntelliJ IDEA.
  User: lukss
  Date: 6/8/2025
  Time: 4:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    BigDecimal totalprice = new BigDecimal(0);
    DataManager dataManager = (DataManager) application.getAttribute("datamanager");
    ShoppingCart cart = (ShoppingCart) application.getAttribute("shoppingcart");
    ArrayList<DataManager.item> items = dataManager.getItems(new HashSet<>( cart.shoppingcart.keySet()));
%>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
<h1>Shopping Cart</h1>
<form action="ShoppingCartServlet" method="post">

        <%
                for (DataManager.item item : items) {
                    BigDecimal quantity = BigDecimal.valueOf(cart.shoppingcart.get(item.id));
                    totalprice = totalprice.add(item.price.multiply(quantity));
                    out.print("<input type ='number' value='" + quantity + "' name='" + item.id + "'>" + item.name + ", " + item.price + "<br>");
                }
        %>
    Total: $ <%= totalprice %> <input type="submit" value="Update Cart"/>
</form>
<a href="index.jsp">Continue shopping</a>
</body>
</html>
