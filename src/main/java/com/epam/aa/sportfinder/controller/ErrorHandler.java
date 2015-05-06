package com.epam.aa.sportfinder.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ErrorHandler", urlPatterns = "/error")
public class ErrorHandler extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Throwable throwable = (Throwable)
//                req.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer)
                req.getAttribute("javax.servlet.error.status_code");
//        String servletName = (String)
//                req.getAttribute("javax.servlet.error.servlet_name");
//        if (servletName == null){
//            servletName = "Unknown";
//        }
//
//        String requestUri = (String)
//                req.getAttribute("javax.servlet.error.request_uri");
//        if (requestUri == null){
//            requestUri = "Unknown";
//        }
        req.getRequestDispatcher("/WEB-INF/views/404.jsp").forward(req, resp);
    }
}
