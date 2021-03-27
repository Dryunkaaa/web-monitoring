package com.monitoring.controller;

import com.monitoring.domain.URL;
import com.monitoring.entity.Message;
import com.monitoring.service.MonitoringService;
import com.monitoring.storage.UrlRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IndexController extends Controller {

    private Map<Long, Message> messageMap;
    private List<URL> monitoringUrls;

    private UrlRepository urlRepository;

    public IndexController(Map<Long, Message> messageMap, List<URL> monitoringUrls, UrlRepository urlRepository) {
        this.messageMap = messageMap;
        this.monitoringUrls = monitoringUrls;
        this.urlRepository = urlRepository;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MonitoringService monitoringService = new MonitoringService(monitoringUrls, messageMap);

        List<URL> urlList = new ArrayList<>();
        urlRepository.getListData().forEach(o -> urlList.add((URL) o));

        monitoringService.startMonitoring(urlList);

        request.setAttribute("urls", urlList);
        request.setAttribute("messageMap", messageMap);
        response.setIntHeader("Refresh", 5);

        request.getServletContext().getRequestDispatcher("/views/index.jsp").forward(request, response);
    }
}
