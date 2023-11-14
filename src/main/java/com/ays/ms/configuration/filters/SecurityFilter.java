package com.ays.ms.configuration.filters;

import com.ays.ms.exceptions.AuthenticationAYSException;
import com.ays.ms.service.AuthenticationService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.ays.ms.configuration.ListURI;

@Component
@Order(1)
public class SecurityFilter implements Filter {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        String method = request.getMethod();

        String homePage = request.getContextPath() + "/";

        if (Boolean.FALSE.equals(this.isWhiteListUri(uri, method))
            && Boolean.FALSE.equals(authenticationService.isLogged())) {
            servletRequest = new HttpServletRequestWrapper((HttpServletRequest) request) {
                @Override
                public String getRequestURI() {
                    return "/";
                }
            };
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isWhiteListUri(String uri, String method) {

        boolean isWhiteList = Boolean.FALSE;

        List<String> authWitheList;


        switch (method) {
            case "GET":
                authWitheList = Arrays.asList(ListURI.AUTH_WHITELIST_GET);
                break;
            case "POST":
                authWitheList = Arrays.asList(ListURI.AUTH_WHITELIST_POST);
                break;
            default:
                return false;
        }

        for (int i = 0; i < authWitheList.size() && !isWhiteList; i++) {

            String authWhiteUri = authWitheList.get(i);

            isWhiteList = (uri.equals(authWhiteUri));

            if ((authWhiteUri.contains("**")) && Boolean.FALSE.equals(isWhiteList)) {

                String path = authWhiteUri.split("\\*\\*")[0];

                if (path.substring(path.length() - 1).equals("/")) {
                    path = path.substring(0, path.length() - 1);
                }

                isWhiteList = uri.startsWith(path);

            }

        }

        return isWhiteList;
    }


}
