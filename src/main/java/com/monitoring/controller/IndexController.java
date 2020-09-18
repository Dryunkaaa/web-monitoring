package com.monitoring.controller;

import com.monitoring.domain.URL;
import com.monitoring.service.MonitoringService;
import com.monitoring.storage.UrlStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class IndexController extends Controller {

    private static MonitoringService monitoringService = new MonitoringService();

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<URL> urlList = new UrlStorage().getAll();
        request.setAttribute("urls", urlList);
        monitoringService.startMonitoring(urlList);

        response.setIntHeader("Refresh", 5);
        request.getServletContext().getRequestDispatcher("/views/index.jsp").forward(request, response);
    }

}
