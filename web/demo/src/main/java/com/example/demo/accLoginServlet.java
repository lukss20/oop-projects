package com.example.demo;


import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
@WebServlet("/accLoginServlet")
public class accLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountManager accountManager = (AccountManager) request.getServletContext().getAttribute("accountManager");
        if (accountManager.correctPassword(request.getParameter("name"),request.getParameter("password"))) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("accWelcome.jsp");
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("accLoginFail.jsp");
            requestDispatcher.forward(request, response);
        }

    }
}
