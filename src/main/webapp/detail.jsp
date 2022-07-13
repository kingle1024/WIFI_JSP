<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="db.Member" %>
<%@page import="java.util.List" %>
<%@page import="db.MemberService" %>
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
    String memberType = request.getParameter("memberType");
    String userId = request.getParameter("userId");

    MemberService memberService = new MemberService();
    Member member = memberService.detail(memberType, userId);

%>
    <h1>회원 상세</h1>
    <table>
        <colgroup>
            <col style="width: 20%;" />
            <col style="width: 80%;" />
        </colgroup>
        <tbody>
            <tr>
                <th>회원구분</th>
                <td><%=member.getMemberType()%></td>
                <th>아이디</th>
                <td><%=member.getUserId()%></td>
                <th>비밀번호</th>
                <td><%=member.getPassword()%></td>
                <th>이름</th>
                <td><%=member.getName()%></td>
            </tr>
        </tbody>
    </table>
    <div>
        <a href="list.jsp"> list </a>
    </div>
</body>
</html>