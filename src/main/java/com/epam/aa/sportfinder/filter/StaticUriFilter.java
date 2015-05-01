package com.epam.aa.sportfinder.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO: Cache control filter, avoid browser caching
public class StaticUriFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();
        if(!requestURI.contains("assets")) {
            StringBuffer buffer = new StringBuffer("/controller");
            buffer.append(requestURI);
            request.getRequestDispatcher(buffer.toString()).forward(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
