package com.monitoring.controller;

import com.monitoring.domain.URL;
import com.monitoring.entity.Message;
import com.monitoring.service.MonitoringService;
import com.monitoring.storage.UrlStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IndexController extends Controller {

    public static Map<Long, Message> messageMap = new ConcurrentHashMap<>();
    public static List<URL> monitoringUrls = new ArrayList<>();

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MonitoringService monitoringService = new MonitoringService();
        List<URL> urlList = new UrlStorage().getAll();

        monitoringService.startMonitoring(urlList);

        request.setAttribute("urls", urlList);

        request.setAttribute("messageMap", messageMap);
        response.setIntHeader("Refresh", 5);
        request.getServletContext().getRequestDispatcher("/views/index.jsp").forward(request, response);
    }

}
