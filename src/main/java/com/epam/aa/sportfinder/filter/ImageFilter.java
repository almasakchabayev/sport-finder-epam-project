package com.epam.aa.sportfinder.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ImageFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(PermissionsFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestURI = request.getRequestURI();

        if ("/image".equals(requestURI)) {
            String path = requestURI + "?" + request.getQueryString();
            logger.debug("request for image, forwarding to " + path);
            request.getRequestDispatcher(path).forward(request, response);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
