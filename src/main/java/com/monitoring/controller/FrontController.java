package com.monitoring.controller;

import com.monitoring.controller.url.EditUrlController;
import com.monitoring.controller.url.RemoveUrlController;
import com.monitoring.controller.url.UrlAddController;
import com.monitoring.controller.url.MonitoringStatusController;
import com.monitoring.service.UrlService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter(value = {"/*", "/**"})
public class FrontController implements Filter {

    private Map<String, Controller> handlers;

    public void init(FilterConfig filterConfig) throws ServletException {
        handlers = new HashMap<>();

        handlers.put("/", new IndexController());
        handlers.put("/addUrl", new UrlAddController());
        handlers.put("/changeMonitoringStatus", new MonitoringStatusController());
        handlers.put("/edit", new EditUrlController());
        handlers.put("/delete", new RemoveUrlController());
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletRequest.setCharacterEncoding("UTF-8");

        String uri = httpServletRequest.getRequestURI();

        if (uri.endsWith(".jsp")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (!uri.endsWith(".ico")){

            try {
                UrlService urlService = new UrlService();
                String validUrl = urlService.removeParameters(uri);

                handlers.get(validUrl).process(httpServletRequest, httpServletResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void destroy() {
    }
}
