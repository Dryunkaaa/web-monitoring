package com.monitoring.storage;

import com.monitoring.domain.URL;
import com.monitoring.entity.ResponseStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UrlStorage extends Storage {

    private PreparedStatement createStatement;

    public UrlStorage() {
        try {
            String createQuery = "insert into urls (path, monitoring_period,ok_response_time, warning_response_time," +
                    "critical_response_time,excepted_response_code, min_response_size, max_response_size, " +
                    "response_substring, response_status) values (?, ?, ?, ?,?,?,?,?,?, ?)";
            createStatement = connection.prepareStatement(createQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUrl(URL url) {
        try {
            createStatement.setString(1, url.getPath());
            createStatement.setLong(2, url.getMonitoringPeriod());
            createStatement.setLong(3, url.getOkResponseTime());
            createStatement.setLong(4, url.getWarningResponseTime());
            createStatement.setLong(5, url.getCriticalResponseTime());
            createStatement.setInt(6, url.getExceptedResponseCode());
            createStatement.setLong(7, url.getMinResponseSize());
            createStatement.setLong(8, url.getMaxResponseSize());
            createStatement.setString(9, url.getResponseSubstring());
            createStatement.setString(10, url.getResponseStatus().toString());

            createStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<URL> getAll() {
        String query = "select* from urls";
        List<URL> urls = new ArrayList<URL>();

        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {
                long id = rs.getLong("id");
                String path = rs.getString("path");
                long monitoringPeriod = rs.getLong("monitoring_period");
                long okResponseTime = rs.getLong("ok_response_time");
                long warningResponseTime = rs.getLong("warning_response_time");
                long criticalResponseTime = rs.getLong("critical_response_time");
                int responseCode = rs.getInt("excepted_response_code");
                long minResponseSize = rs.getLong("min_response_size");
                long maxResponseSize = rs.getLong("max_response_size");
                String responseSubstring = rs.getString("response_substring");
                String responseStatus = rs.getString("response_status");

                URL url = new URL();
                url.setId(id);
                url.setPath(path);
                url.setMonitoringPeriod(monitoringPeriod);
                url.setOkResponseTime(okResponseTime);
                url.setWarningResponseTime(warningResponseTime);
                url.setCriticalResponseTime(criticalResponseTime);
                url.setExceptedResponseCode(responseCode);
                url.setMinResponseSize(minResponseSize);
                url.setMaxResponseSize(maxResponseSize);
                url.setResponseSubstring(responseSubstring);
                url.setResponseStatus(ResponseStatus.valueOf(responseStatus));

                urls.add(url);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return urls;
    }

    public void updateResponseStatus(URL url, ResponseStatus responseStatus) {
        String query = "update urls set response_status = '" + responseStatus.toString() + "' where id = " + url.getId();

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
