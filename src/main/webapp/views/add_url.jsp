<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding URL</title>
</head>
<body>

<form action="/addUrl" method="post">
    <input type="text" placeholder="URL" name="path" style="width: 92%">
    <br>
    <input type="number" placeholder="Monitoring period (milliseconds)" name="monitoringPeriod" style="width: 92%">
    <br>
    <input type="number" placeholder="Response time for 'OK' status" name="okResponseTime" style="width: 30%">
    <input type="number" placeholder="Response time for 'WARNING' status" name="warningResponseTime" style="width: 30%">
    <input type="number" placeholder="Response time for 'CRITICAL' status" name="criticalResponseTime" style="width: 30%">
    <br>
    <input type="number" placeholder="Expected response code" name="responseCode" style="width: 92%">
    <br>
    <input type="number" placeholder="Min response size (bytes)" name="minResponseSize" style="width: 92%">
    <input type="number" placeholder="Max response size (bytes)" name="maxResponseSize" style="width: 92%">
    <br>

    <input type="text" placeholder="Response substring (set empty field if not want to set)" name="responseSubstring" style="width: 92%">

    <button type="submit">Add url</button>
</form>


<a href="/">View all Url's</a>

</body>
</html>
