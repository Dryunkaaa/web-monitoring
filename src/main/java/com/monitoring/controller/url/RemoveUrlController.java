package com.monitoring.controller.url;

import com.monitoring.controller.Controller;
import com.monitoring.domain.URL;
import com.monitoring.service.UrlService;
import com.monitoring.storage.UrlRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RemoveUrlController extends Controller {

    private UrlRepository urlRepository;
    private List<URL> monitoringUrls;

    public RemoveUrlController(List<URL> monitoringUrls, UrlRepository urlRepository) {
        this.monitoringUrls = monitoringUrls;
        this.urlRepository = urlRepository;
    }

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = Long.parseLong(request.getParameter("urlId"));

        UrlService urlService = new UrlService();

        URL url = urlService.findUrlById(monitoringUrls, id);

        if (url.getThread() != null) {
            url.getThread().interrupt();
            url.getThread().interrupt();
        }

        monitoringUrls.remove(url);
        urlRepository.remove(urlRepository.getDataById(id));

        response.sendRedirect("/");
    }
}
