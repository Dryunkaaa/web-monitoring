package com.monitoring.controller.url;

import com.monitoring.controller.Controller;
import com.monitoring.controller.IndexController;
import com.monitoring.domain.URL;
import com.monitoring.service.MonitoringService;
import com.monitoring.storage.UrlStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MonitoringStatusController extends Controller {

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = Long.parseLong(request.getParameter("id"));
        UrlStorage urlStorage = new UrlStorage();

        URL url = urlStorage.getById(id);
        urlStorage.updateMonitoringStatus(url);

        MonitoringService monitoringService = new MonitoringService();
        monitoringService.startMonitoring(urlStorage.getAll());

        List<URL> monitoringUrls = IndexController.monitoringUrls;

        if (monitoringUrls.size() > 0) {
            int urlIndex = monitoringUrls.indexOf(url);
            URL monitoringUrl = monitoringUrls.get(urlIndex);
            monitoringUrl.setMonitoringStatus(!url.enabledMonitoringStatus());
        }

        response.sendRedirect("/");
    }
}
