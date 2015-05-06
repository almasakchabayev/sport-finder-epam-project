package com.epam.aa.sportfinder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ErrorHandler", urlPatterns = "/error")
public class ErrorHandler extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer statusCode = (Integer)
                req.getAttribute("javax.servlet.error.status_code");

        req.setAttribute("statusCode", statusCode);

        logger.info("error during page load with errorStatus {}", statusCode);
        req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
    }
}
