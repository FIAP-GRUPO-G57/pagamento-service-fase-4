package br.com.fiap.pagamento.service.infra.controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ContentTypeOptionsFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletResponse instanceof HttpServletResponse) {
            ((HttpServletResponse) servletResponse).setHeader("X-Content-Type-Options", "nosniff");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
