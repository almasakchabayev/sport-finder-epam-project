package com.epam.aa.sportfinder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FrontController", urlPatterns = "/controller/*")
public class FrontController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            Action action = ActionFactory.getAction(request);

            if (action == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            String view = action.execute(request, response);

            if (view.contains("redirect:")) {
                String redirectView = view.replace("redirect:", "");
                response.sendRedirect(redirectView);
                return;
            }

            String requestDispatcherString = "/WEB-INF/views/" + view + ".jsp";
            logger.debug("Request dispatching to {}", requestDispatcherString);
            request.getRequestDispatcher(requestDispatcherString).forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Executing action failed.", e);
        }
    }
}