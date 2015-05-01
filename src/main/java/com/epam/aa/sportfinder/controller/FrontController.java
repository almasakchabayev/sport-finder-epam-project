package com.epam.aa.sportfinder.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FrontController", urlPatterns = "/controller/*")
public class FrontController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            Action action = ActionFactory.getAction(request);
            // TODO: use sendError
            String view = "404";
            if (action != null) {
                view = action.execute(request, response);
            }
            request.getRequestDispatcher("/WEB-INF/views/" + view + ".jsp").forward(request, response);
            // add P R G
        } catch (Exception e) {
            throw new ServletException("Executing action failed.", e);
        }
    }
}