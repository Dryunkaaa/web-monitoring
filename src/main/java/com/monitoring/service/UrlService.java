package com.monitoring.service;

import com.monitoring.domain.URL;

import java.util.List;

public class UrlService {

    public String removeParameters(String url){
        return url.split("\\?")[0];
    }

    public URL findUrlById(List<URL> urls, long urlId){
        for (URL url : urls){
            if (url.getId() == urlId){
                return url;
            }
        }

        return new URL();
    }
}
