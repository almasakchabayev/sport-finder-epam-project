package com.epam.aa.sportfinder.filter;

import com.epam.aa.sportfinder.config.ControllerActionLoader;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class AuthorizationCheckFilter implements Filter {

    Map<String, String> pathRoleMap;

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        Set<Class<?>> annotated = ControllerActionLoader.getClassesAnnotatedWithControllerAction();

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
