package com.monitoring.controller.url;

import com.monitoring.controller.Controller;
import com.monitoring.controller.IndexController;
import com.monitoring.domain.URL;
import com.monitoring.service.UrlService;
import com.monitoring.storage.UrlDatabaseStorage;
import com.monitoring.storage.UrlRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveUrlController extends Controller {

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = Long.parseLong(request.getParameter("urlId"));
        UrlRepository urlRepository = new UrlDatabaseStorage();

        UrlService urlService = new UrlService();

        URL url = urlService.findUrlById(IndexController.monitoringUrls, id);

        if (url.getThread() != null){
            url.getThread().interrupt();
            url.getThread().interrupt();
        }

        IndexController.monitoringUrls.remove(url);

        urlRepository.remove(urlRepository.getDataById(id));

        response.sendRedirect("/");
    }
}
