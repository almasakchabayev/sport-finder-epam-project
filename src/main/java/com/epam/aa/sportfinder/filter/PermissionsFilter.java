package com.epam.aa.sportfinder.filter;

import com.epam.aa.sportfinder.config.ControllerActionsLoader;
import com.epam.aa.sportfinder.controller.ActionFactory;
import com.epam.aa.sportfinder.controller.ControllerAction;
import com.epam.aa.sportfinder.model.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.epam.aa.sportfinder.controller.ControllerAction.*;

public class PermissionsFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(PermissionsFilter.class);

    Map<String, String[]> pathPermissions = new HashMap<>();

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
        HttpServletResponse response = (HttpServletResponse) resp;
        String path = ActionFactory.getPath(request);
        logger.debug("permission filter checks path: {}", path);

        String[] notAllowedPermissions = pathPermissions.get(path);
        if (notAllowedPermissions != null) {
            HttpSession session = request.getSession(false);

            for (String notAllowedPermission : notAllowedPermissions) {
                if (notAllowedPermission.equals(Permission.GUEST)) {
                    if (session == null || session.getAttribute("user") == null) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                } else if (notAllowedPermission.equals(Permission.CUSTOMER)) {
                    //todo add customer handler
                } else if (notAllowedPermission.equals(Permission.MANAGER)) {
                    if (session == null)
                        continue;

                    Object user = session.getAttribute("user");
                    if (user instanceof Manager) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                }
            }
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
