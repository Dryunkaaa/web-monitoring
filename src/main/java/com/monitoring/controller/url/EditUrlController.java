package com.monitoring.controller.url;

import com.monitoring.controller.Controller;
import com.monitoring.domain.URL;
import com.monitoring.storage.UrlRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditUrlController extends Controller {

    private List<URL> monitoringUrls;
    private UrlRepository urlRepository;

    public EditUrlController(List<URL> monitoringUrls, UrlRepository urlRepository) {
        this.monitoringUrls = monitoringUrls;
        this.urlRepository = urlRepository;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = Long.parseLong(request.getParameter("urlId"));

        URL url = (URL) urlRepository.getDataById(id);

        request.setAttribute("url", url);
        request.getRequestDispatcher("/views/edit_url.jsp").forward(request, response);
    }

    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = Long.parseLong(request.getParameter("id"));
        long monitoringPeriod = Long.parseLong(request.getParameter("monitoringPeriod"));
        long okResponseTime = Long.parseLong(request.getParameter("okResponseTime"));
        long warningResponseTime = Long.parseLong(request.getParameter("warningResponseTime"));
        long criticalResponseTime = Long.parseLong(request.getParameter("criticalResponseTime"));
        int responseCode = Integer.parseInt(request.getParameter("responseCode"));
        long maxResponseSize = Long.parseLong(request.getParameter("maxResponseSize"));
        long minResponseSize = Long.parseLong(request.getParameter("minResponseSize"));
        String responseSubstring = request.getParameter("responseSubstring");

        int monitoringUrlIndex = monitoringUrls.indexOf(urlRepository.getDataById(id));
        URL url;

        if (monitoringUrlIndex != -1) { // method returned '-1' if element didn't found
            url = monitoringUrls.get(monitoringUrlIndex);
        } else {
            url = (URL) urlRepository.getDataById(id);
        }

        url.setMonitoringPeriod(monitoringPeriod);
        url.setOkResponseTime(okResponseTime);
        url.setWarningResponseTime(warningResponseTime);
        url.setCriticalResponseTime(criticalResponseTime);
        url.setExceptedResponseCode(responseCode);
        url.setMaxResponseSize(maxResponseSize);
        url.setMinResponseSize(minResponseSize);
        url.setResponseSubstring(responseSubstring);

        urlRepository.update(url);

        response.sendRedirect("/");
    }
}
