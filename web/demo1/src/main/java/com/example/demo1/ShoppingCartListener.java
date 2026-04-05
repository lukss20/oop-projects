package com.example.demo1;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ShoppingCartListener implements ServletContextListener {
    ShoppingCart shoppingCart;
    public void contextInitialized(ServletContextEvent sce) {
        shoppingCart = new ShoppingCart();
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("shoppingcart", shoppingCart);

    }
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
