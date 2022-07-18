<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List" %>
<%@ page import="db.History" %>
<%@ page import="service.HistoryService" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>

<%
    HistoryService historyService = new HistoryService();
    List<History> historyList = historyService.list();
    int historySize = historyList.size();
%>
    <h1>위치 히스토리 목록</h1>
    <table>
        <thead>
            <tr>
                <td>ID</td>
                <td>X좌표</td>
                <td>Y좌표</td>
                <td>조회일자</td>
                <td>비고</td>
            </tr>
        </thead>
        <tbody>
        <% for (History h : historyList){ %>
        <tr>
            <td><%=historySize--%></td>
            <td><%=h.getLAT()%></td>
            <td><%=h.getLNT()%></td>
            <td><%=h.getDate()%></td>
        </tr>
        <%}%>

        </tbody>
    </table>
    <div>
        <a href="list.jsp"> list </a>
    </div>
</body>
</html>