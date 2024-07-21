<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Success Page</title>
    </head>
    <body>
    <center>
        <% if (request.getAttribute("msg") != null) { %>
            <h3><%= request.getAttribute("msg") %></h3>
        <% } %>
        <br><br>
        <% if (session.getAttribute("fileName") != null) { %>
            <% String file = (String) session.getAttribute("fileName"); %>
            <a href="DownloadServlet?fileName=<%= file %>">Download</a>&nbsp;&nbsp;&nbsp;
        <% } %>
        <a href="file-list.jsp">View List</a>
    </center>
    </body>
</html>
