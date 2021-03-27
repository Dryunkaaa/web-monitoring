package com.monitoring.controller;

import com.monitoring.controller.url.EditUrlController;
import com.monitoring.controller.url.RemoveUrlController;
import com.monitoring.controller.url.UrlAddController;
import com.monitoring.controller.url.MonitoringStatusController;
import com.monitoring.domain.URL;
import com.monitoring.entity.Message;
import com.monitoring.service.UrlService;
import com.monitoring.storage.UrlDatabaseRepository;
import com.monitoring.storage.UrlRepository;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter(value = {"/*", "/**"})
public class FrontController implements Filter {

    private Map<String, Controller> handlers;
    private UrlRepository urlRepository;
    private Map<Long, Message> messageMap;
    private List<URL> monitoringUrls;

    public void init(FilterConfig filterConfig) throws ServletException {
        handlers = new HashMap<>();
        urlRepository = new UrlDatabaseRepository();
        messageMap = new ConcurrentHashMap<>();
        monitoringUrls = new ArrayList<>();

        initHandlers();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletRequest.setCharacterEncoding("UTF-8");

        String uri = httpServletRequest.getRequestURI();

        if (uri.endsWith(".jsp")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (!uri.endsWith(".ico")) {

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

    private void initHandlers() {
        handlers.put("/", new IndexController(messageMap, monitoringUrls, urlRepository));
        handlers.put("/addUrl", new UrlAddController(urlRepository));
        handlers.put("/changeMonitoringStatus", new MonitoringStatusController(messageMap, monitoringUrls, urlRepository));
        handlers.put("/edit", new EditUrlController(monitoringUrls, urlRepository));
        handlers.put("/delete", new RemoveUrlController(monitoringUrls, urlRepository));
    }
}
