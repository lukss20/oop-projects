package com.example.demo;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.annotation.WebServlet;
@WebServlet("/accCreateServlet")
public class accCreateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountManager accountManager = (AccountManager)request.getServletContext().getAttribute("accountManager");
        if(accountManager.checkAccount(request.getParameter("name"))){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("accAlreadyCreated.jsp");
            requestDispatcher.forward(request,response);
        } else{
            accountManager.createAccount(request.getParameter("name"),request.getParameter("password"));
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("accWelcome.jsp");
            requestDispatcher.forward(request,response);
        }
    }
}