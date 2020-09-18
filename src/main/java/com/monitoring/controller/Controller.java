package com.monitoring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller {

    public void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        handle(request, response);

        String methodName = request.getMethod().toUpperCase();
        if (methodName.equals("GET")) {
            handleGet(request, response);
        } else if (methodName.equals("POST")) {
            handlePost(request, response);
        }
    }

    public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }

    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }

    public void handlePost(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }
}
