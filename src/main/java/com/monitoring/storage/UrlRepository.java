package com.monitoring.storage;

import com.monitoring.domain.URL;
import com.monitoring.entity.ResponseStatus;

public interface UrlRepository extends DataSource {

    void updateResponseStatus(URL url, ResponseStatus responseStatus);

    void updateMonitoringStatus(URL url);
}
