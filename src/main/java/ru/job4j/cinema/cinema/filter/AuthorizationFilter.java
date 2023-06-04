package ru.job4j.cinema.cinema.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
@Order(1)
public class AuthorizationFilter extends HttpFilter {

    private final Set<String> allowedLinks1 = Set.of("index", "users", "register", "login", "errors", "logout",
            "{id}", "filmLibrary", "timetable", "buy", "idFS", "logo", "files");

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var uri = request.getRequestURI();
        if (isAlwaysPermitted(uri)) {
            chain.doFilter(request, response);
            return;
        }
        var userLoggedIn = request.getSession().getAttribute("user") != null;
        if (!userLoggedIn) {
            var loginPageUrl = request.getContextPath() + "/users/login";
            response.sendRedirect(loginPageUrl);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isAlwaysPermitted(String uri) {
        String[] split = uri.split("/");
        return split.length > 0
                && (allowedLinks1.contains(split[split.length - 1])
                || (split.length > 2 &&  allowedLinks1.contains(split[split.length - 2]))
        );
    }
}
