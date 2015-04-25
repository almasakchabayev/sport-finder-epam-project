package com.epam.aa.sportfinder.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Filter", urlPatterns = "/*", dispatcherTypes = DispatcherType.REQUEST)
public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

//        final HttpServletRequestWrapper wrapped = new HttpServletRequestWrapper(request) {
//            @Override
//            public StringBuffer getRequestURL() {
//                final StringBuffer originalUrl = ((HttpServletRequest) getRequest()).getRequestURL();
//                return new StringBuffer("http://servername2:7001");
//            }
//        };
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
