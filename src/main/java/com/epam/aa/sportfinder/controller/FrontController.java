package com.epam.aa.sportfinder.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FrontController", urlPatterns = "/pages/*")
public class FrontController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            Action action = ActionFactory.getAction(request);
            System.out.println(request.getRequestURI() + "    " + request.getPathInfo());
            String view = "404";
            if (action != null) {
                view = action.execute(request, response);
            }
            request.getRequestDispatcher("/WEB-INF/views/" + view + ".jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Executing action failed.", e);
        }
    }
}