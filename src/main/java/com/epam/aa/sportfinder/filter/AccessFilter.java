package com.epam.aa.sportfinder.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;

public class AccessFilter implements Filter {

    Map<String, String> pathRoleMap;

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {


        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
