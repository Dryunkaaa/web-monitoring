<%@ page import="com.monitoring.domain.URL" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.monitoring.entity.Message" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<html>
<head>
    <title>URL'S</title>
</head>
<body>

<table>
    <tr>
        <th>ID</th>
        <th>URL</th>
        <th>Status</th>
        <th>Message</th>
    </tr>

    <%
        List<URL> urls = (List<URL>) request.getAttribute("urls");
        Map<Long, Message> errorMap = (Map<Long, Message>) request.getAttribute("messageMap");

        for (URL url : urls) {
            out.println("<tr>");
            out.println("<td>" + url.getId() + "</td>");
            out.println("<td>" + url.getPath() + "</td>");
            out.println("<td>" + url.getResponseStatus().toString() + "</td>");

            if (errorMap.get(url.getId()) != null) {
                out.println("<td>" + errorMap.get(url.getId()).getMessage() + "</td>");
            }else{
                out.println("<td>Unknown</td>");
            }

            if (url.getMonitoringStatus()) {
                out.println("<td><a href='/changeMonitoringStatus?id=" + url.getId() + "'>Stop monitoring</a></td>");
            } else {
                out.println("<td><a href='/changeMonitoringStatus?id=" + url.getId() + "'>Start monitoring</a></td>");
            }

            out.println("<td><a href='/edit?urlId="+ url.getId() + "'>Edit</a></td>");
            out.println("<td><a href='/delete?urlId="+ url.getId() + "'>Remove</a></td>");
        }

    %>

</table>

<br>

<a href="/addUrl">Add new URL</a>

</body>
</html>
