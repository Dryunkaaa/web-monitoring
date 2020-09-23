package com.monitoring.controller.url;

import com.monitoring.controller.Controller;
import com.monitoring.controller.IndexController;
import com.monitoring.domain.URL;
import com.monitoring.entity.Message;
import com.monitoring.service.MonitoringService;
import com.monitoring.storage.UrlRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MonitoringStatusController extends Controller {

    private Map<Long, Message> messageMap;
    private List<URL> monitoringUrls;
    private UrlRepository urlRepository;

    public MonitoringStatusController(Map<Long, Message> messageMap, List<URL> monitoringUrls, UrlRepository urlRepository) {
        this.messageMap = messageMap;
        this.monitoringUrls = monitoringUrls;
        this.urlRepository = urlRepository;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MonitoringService monitoringService = new MonitoringService(monitoringUrls, messageMap);

        long id = Long.parseLong(request.getParameter("id"));

        URL url = (URL) urlRepository.getDataById(id);
        urlRepository.updateMonitoringStatus(url);

        List<URL> urls = new ArrayList<>();
        urlRepository.getListData().forEach(o -> urls.add((URL) o));

        monitoringService.startMonitoring(urls);

        if (monitoringUrls.size() > 0) {
            int urlIndex = monitoringUrls.indexOf(url);
            URL monitoringUrl = monitoringUrls.get(urlIndex);
            monitoringUrl.setMonitoringStatus(!url.enabledMonitoringStatus());
        }

        response.sendRedirect("/");
    }
}
