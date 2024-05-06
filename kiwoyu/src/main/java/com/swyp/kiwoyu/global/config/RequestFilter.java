package com.swyp.kiwoyu.global.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestFilter implements Filter {
    private FilterConfig filterConfig = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request = new RequestWrapper((HttpServletRequest)request);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfiguration) {
        this.filterConfig = filterConfiguration;
    }

    public void destroy() {
        this.filterConfig = null;
    }

}