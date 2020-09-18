package com.monitoring.controller;

import com.monitoring.domain.URL;
import com.monitoring.entity.ResponseStatus;
import com.monitoring.storage.UrlStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlAddController extends Controller {

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("views/add_url.jsp").forward(request, response);
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getParameter("path");
        long monitoringPeriod = Long.parseLong(request.getParameter("monitoringPeriod"));
        long okResponseTime = Long.parseLong(request.getParameter("okResponseTime"));
        long warningResponseTime = Long.parseLong(request.getParameter("warningResponseTime"));
        long criticalResponseTime = Long.parseLong(request.getParameter("criticalResponseTime"));
        int responseCode = Integer.parseInt(request.getParameter("responseCode"));
        long maxResponseSize = Long.parseLong(request.getParameter("maxResponseSize"));
        long minResponseSize = Long.parseLong(request.getParameter("minResponseSize"));
        String responseSubstring = request.getParameter("responseSubstring");

        URL url = new URL();
        url.setPath(path);
        url.setMonitoringPeriod(monitoringPeriod);
        url.setOkResponseTime(okResponseTime);
        url.setWarningResponseTime(warningResponseTime);
        url.setCriticalResponseTime(criticalResponseTime);
        url.setExceptedResponseCode(responseCode);
        url.setMinResponseSize(minResponseSize);
        url.setMaxResponseSize(maxResponseSize);
        url.setResponseSubstring(responseSubstring);
        url.setResponseStatus(ResponseStatus.OK);

        UrlStorage urlStorage = new UrlStorage();
        urlStorage.addUrl(url);

        response.sendRedirect("/");
    }
}
