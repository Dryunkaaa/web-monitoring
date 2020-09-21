<%@ page import="com.monitoring.domain.URL" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<form action="/edit" method="post">

    <%
        URL url = (URL) request.getAttribute("url");

        out.println("<input type='number' hidden name='id' value='" + url.getId() + "'>");
        out.println("<input type='text' hidden name='path' value='" + url.getPath() + "'>");

        out.println("<h1>Settings of " + url.getPath() + "</h1>");
        out.println("<p>Monitoring period (in milliseconds)</p>");
        out.println("<input type='number' name='monitoringPeriod' value='" + url.getMonitoringPeriod()+"'>");
        out.println("<br>");
        out.println("<p>Response time for 'OK' status (in milliseconds)</p>");
        out.println("<input type='number' name='okResponseTime' value='" + url.getOkResponseTime()+"'>");
        out.println("<br>");
        out.println("<p>Response time for 'WARNING' status (in milliseconds)</p>");
        out.println("<input type='number' name='warningResponseTime' value='" + url.getWarningResponseTime()+"'>");
        out.println("<br>");
        out.println("<p>Response time for 'CRITICAL' status (in milliseconds)</p>");
        out.println("<input type='number' name='criticalResponseTime' value='" + url.getCriticalResponseTime()+"'>");
        out.println("<br>");
        out.println("<p>Expected response code</p>");
        out.println("<input type='number' name='responseCode' value='" + url.getExceptedResponseCode()+"'>");
        out.println("<br>");
        out.println("<p>Min response size (in bytes)</p>");
        out.println("<input type='number' name='minResponseSize' value='" + url.getMinResponseSize()+"'>");
        out.println("<br>");
        out.println("<p>Max response size (in bytes)</p>");
        out.println("<input type='number' name='maxResponseSize' value='" + url.getMaxResponseSize()+"'>");
        out.println("<br>");
        out.println("<p>Response substring (set empty field if not want to set)</p>");
        out.println("<input type='number' name='responseSubstring' value='" + url.getResponseSubstring()+"'>");
    %>

    <br>
    <button type="submit">Edit url</button>
</form>
</body>
</html>
