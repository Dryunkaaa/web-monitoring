package com.monitoring.storage;

import java.util.List;

public interface DataSource {

    Object getDataById(long id);

    List<Object> getListData();

    void remove(Object data);

    void update(Object data);

    void create(Object data);
}
