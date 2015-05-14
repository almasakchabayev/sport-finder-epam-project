package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Image;
import com.epam.aa.sportfinder.service.ImageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ImageServlet", urlPatterns = "/image")
public class ImageServlet extends HttpServlet {

    @Override
    protected long getLastModified(HttpServletRequest req) {
        String queryString = req.getQueryString();
        if (queryString != null || queryString.matches("id=\\d+")) {
            Integer id = Integer.valueOf(queryString.substring(queryString.indexOf("=") + 1));
            Long modifiedAt = ImageService.getModifiedAt(id);
            return modifiedAt != null ? modifiedAt : -1;
        }
        return -1;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryString = request.getQueryString();
        if (queryString == null || !queryString.matches("id=\\d+")) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }

        Integer id = Integer.valueOf(queryString.substring(queryString.indexOf("=") + 1));
        Image image = ImageService.findById(id);
        response.setContentType("image/jpg");
        response.getOutputStream().write(image.getImageArray());
        response.getOutputStream().flush();
    }
}
