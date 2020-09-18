<%@ page import="com.monitoring.domain.URL" %>
<%@ page import="java.util.List" %>
<%@ page import="com.monitoring.service.UrlService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
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
    </tr>

    <%
        List<URL> urls = (List<URL>) request.getAttribute("urls");

        for (URL url : urls) {
            out.println("<tr>");
            out.println("<td>" + url.getId() + "</td>");
            out.println("<td>" + url.getPath() + "</td>");
            out.println("<td>" + url.getResponseStatus().toString() + "</td>");

            if (url.isWorkingStatus()){
                out.println("<td><a href='/stopmonitoring?id=" + url.getId() + "'>Stop monitoring</a></td>");
            }else{
                out.println("<td><a href='/startmonitoring?id=" + url.getId() + "'>Start monitoring</a></td>");
            }
        }

    %>

</table>

<br>

<a href="/addUrl">Add new URL</a>

</body>
</html>
