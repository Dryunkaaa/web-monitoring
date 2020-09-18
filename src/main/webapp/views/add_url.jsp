<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding URL</title>
</head>
<body>

<form action="/addUrl" method="post">
    <input type="text" placeholder="URL" name="path">
    <br>
    <input type="number" placeholder="Monitoring period" name="monitoringPeriod">
    <br>
    <input type="number" placeholder="Response time for 'OK' status" name="okResponseTime">
    <input type="number" placeholder="Response time for 'WARNING' status" name="warningResponseTime">
    <input type="number" placeholder="Response time for 'CRITICAL' status" name="criticalResponseTime">
    <br>
    <input type="number" placeholder="Expected response code" name="responseCode">
    <br>
    <input type="number" placeholder="Min response size" name="minResponseSize">
    <input type="number" placeholder="Max response size" name="maxResponseSize">
    <br>

    <input type="text" placeholder="Response substring" name="responseSubstring">

    <button type="submit">Add url</button>
</form>


<a href="/">View all Url's</a>

</body>
</html>
