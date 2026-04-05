package com.example.demo1;

import java.io.*;
import java.util.Enumeration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/ShoppingCartServlet")
public class ShoppingCartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCart cart = (ShoppingCart) request.getServletContext().getAttribute("shoppingcart");
        String itemID = request.getParameter("itemID");

        if (itemID != null) {
            System.out.println("shemovedi");
            cart.addItem(itemID, "1");
        } else {
            Enumeration<String> parameterNames = request.getParameterNames();
            ShoppingCart tempcart = new ShoppingCart();
            while (parameterNames.hasMoreElements()) {
                String tempid = parameterNames.nextElement();
                String quantity = request.getParameter(tempid);
                tempcart.addItem(tempid, quantity);
            }
            cart = tempcart;
        }
        request.getServletContext().setAttribute("shoppingcart", cart);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("itemcart.jsp");
        requestDispatcher.forward(request, response);


}
}