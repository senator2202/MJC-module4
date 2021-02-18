package com.epam.esm.controller.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Personal data filter. Does not allow authenticated users to see other users orders.
 * Compares authenticated userId and userId in request. In case they don't match, sends error with FORBIDDEN status.
 */
@Component
public class PersonalDataFilter extends GenericFilter {

    private static final String URI_SEPARATOR = "/";
    private static final int USER_ID_INDEX = 3;
    private static final String USER_ORDERS_URL_PATTERN = "/api/users/[1-9]\\d{0,18}/orders";
    private static final String USER_ORDER_RL_PATTERN = USER_ORDERS_URL_PATTERN + "/[1-9]\\d{0,18}";

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (uri.matches(USER_ORDER_RL_PATTERN) || uri.matches(USER_ORDERS_URL_PATTERN)) {
            String[] uriSplit = uri.split(URI_SEPARATOR);
            Long userId = Long.parseLong(uriSplit[USER_ID_INDEX]);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AuthenticationDetails details = (AuthenticationDetails) authentication.getDetails();
            if (!details.isAdmin() && !userId.equals(details.getUserId())) {
                ((HttpServletResponse) servletResponse).sendError(HttpStatus.FORBIDDEN.value());
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
