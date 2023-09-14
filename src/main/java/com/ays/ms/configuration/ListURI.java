package com.ays.ms.configuration;

public class ListURI {

    private ListURI(){}

    public static final String[] AUTH_WHITELIST_GET = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/",
            "/css/**",
            "/img/**",
            "/js/**"
    };

    public static final String[] AUTH_WHITELIST_POST = {
            "/user",
            "/user/login"
    };
}
