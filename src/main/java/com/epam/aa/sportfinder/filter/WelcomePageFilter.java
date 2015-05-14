package com.epam.aa.sportfinder.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomePageFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();
        HttpServletResponse response = (HttpServletResponse) resp;
        if(requestURI.equals("/")) {
            StringBuffer buffer = new StringBuffer(requestURI);
            buffer.append("index");
            response.sendRedirect(buffer.toString());
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
