package com.monitoring.storage;

import com.monitoring.domain.URL;
import com.monitoring.entity.ResponseStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UrlDatabaseRepository extends DatabaseStorage implements UrlRepository {

    private PreparedStatement createStatement;
    private PreparedStatement updateStatement;

    public UrlDatabaseRepository() {
        initStatements();
    }

    @Override
    public Object getDataById(long id) {
        String query = "select* from urls where id = " + id;

        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {
                return getUrlWithProperties(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return new URL();
    }

    @Override
    public List<Object> getListData() {
        String query = "select* from urls";
        List<Object> urls = new ArrayList<>();

        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {
                urls.add(getUrlWithProperties(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }

        return urls;
    }

    @Override
    public void remove(Object data) {
        URL url = (URL) data;
        long id = url.getId();

        String query = "delete from urls where id = " + id;

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object data) {
        URL url = (URL) data;

        try {
            updateStatement.setString(1, url.getPath());
            updateStatement.setLong(2, url.getMonitoringPeriod());
            updateStatement.setLong(3, url.getOkResponseTime());
            updateStatement.setLong(4, url.getWarningResponseTime());
            updateStatement.setLong(5, url.getCriticalResponseTime());
            updateStatement.setInt(6, url.getExceptedResponseCode());
            updateStatement.setLong(7, url.getMinResponseSize());
            updateStatement.setLong(8, url.getMaxResponseSize());
            updateStatement.setString(9, url.getResponseSubstring());
            updateStatement.setString(10, url.getResponseStatus().toString());
            updateStatement.setBoolean(11, true);
            updateStatement.setLong(12, url.getId());

            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Object data) {
        URL url = (URL) data;

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
            createStatement.setBoolean(11, true);

            createStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateResponseStatus(URL url, ResponseStatus responseStatus) {
        String query = "update urls set response_status = '" + responseStatus.toString() + "' where id = " + url.getId();

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMonitoringStatus(URL url) {
        boolean newMonitoringStatus = !url.enabledMonitoringStatus();

        String query = "update urls set monitoring_status = " + newMonitoringStatus + " where id = " + url.getId();

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initStatements() {
        StringBuilder builder = new StringBuilder();
        StringBuilder updateBuilder = new StringBuilder();

        builder.append("insert into urls (path, monitoring_period,ok_response_time, warning_response_time,")
                .append("critical_response_time,excepted_response_code, min_response_size, max_response_size, ")
                .append("response_substring, response_status, monitoring_status) values (?,?,?,?,?,?,?,?,?,?,?)");

        updateBuilder.append("update urls set path = ?, monitoring_period=?, ok_response_time=?,")
                .append("warning_response_time=?, critical_response_time=?, excepted_response_code=?, min_response_size=?,")
                .append("max_response_size=?, response_substring=?,response_status=?, monitoring_status=? where id = ?");

        try {
            createStatement = connection.prepareStatement(builder.toString());
            updateStatement = connection.prepareStatement(updateBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private URL getUrlWithProperties(ResultSet rs) {
        try {
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
            boolean monitoringStatus = rs.getBoolean("monitoring_status");

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
            url.setMonitoringStatus(monitoringStatus);

            return url;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new URL();
    }

}
