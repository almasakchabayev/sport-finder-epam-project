package com.epam.aa.sportfinder.filter;

import com.epam.aa.sportfinder.config.ControllerActionsLoader;
import com.epam.aa.sportfinder.controller.ActionFactory;
import com.epam.aa.sportfinder.controller.ControllerAction;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static com.epam.aa.sportfinder.controller.ControllerAction.*;

public class AuthorizationCheckFilter implements Filter {

    Map<String, String[]> pathPermissions;

    @Override
    public void init(FilterConfig config) throws ServletException {
        Set<Class<?>> annotated = ControllerActionsLoader.getClassesAnnotatedWithControllerAction();

        for (Class<?> action : annotated) {
            ControllerAction annotation = action.getAnnotation(ControllerAction.class);
            String path = annotation.path();
            String[] permissions = annotation.accessDeniedTo();

            pathPermissions.put(path, permissions);
        }

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String path = ActionFactory.getPath(request);

        String[] permissions = pathPermissions.get(path);
        if (permissions != null) {
            for (String permission : permissions) {
                if (permission.equals(Permission.GUEST)) {
                    handleGuestPermission(request);
                } else if (permission.equals(Permission.CUSTOMER)) {
                    //todo add customer handler
                } else if (permission.equals(Permission.MANAGER)) {
                    handleManagerPermission(request);
                }
            }
        }

        chain.doFilter(req, resp);
    }

    private void handleManagerPermission(HttpServletRequest request) {

    }

    private void handleGuestPermission(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return;

        Object user = session.getAttribute("user");
        if (user == null)
            return;

    }

    @Override
    public void destroy() {

    }
}
