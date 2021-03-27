package com.monitoring.storage;

import com.monitoring.domain.URL;
import com.monitoring.entity.ResponseStatus;

import java.util.List;

public class UrlXmlRepository extends XmlStorage implements UrlRepository {

    @Override
    public void updateResponseStatus(URL url, ResponseStatus responseStatus) {
    }

    @Override
    public void updateMonitoringStatus(URL url) {
    }

    @Override
    public Object getDataById(long id) {
        return null;
    }

    @Override
    public List<Object> getListData() {
        return null;
    }

    @Override
    public void remove(Object data) {
    }

    @Override
    public void update(Object data) {
    }

    @Override
    public void create(Object data) {
    }
}
