package com.epam.aa.sportfinder.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StaticUriFilter implements javax.servlet.Filter {
    private static final Logger logger = LoggerFactory.getLogger(StaticUriFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();
        if(!requestURI.contains("assets")) {
            StringBuffer buffer = new StringBuffer("/controller");
            buffer.append(requestURI);
            logger.debug("request for controller action, dispatching to {}", requestURI);
            request.getRequestDispatcher(buffer.toString()).forward(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
