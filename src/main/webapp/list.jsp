<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="db.Member" %>
<%@page import="java.util.List" %>
<%@page import="db.MemberService" %>

<%@ page import="db.Wifi" %>
<%@ page import="db.WifiService" %>
<%
    WifiService wifiService = new WifiService();
    List<Wifi> wifiList = wifiService.list();
%>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<h1>와이파이 정보 구하기
</h1>
<br/>
<a href="#">홈</a> | <a href="#">위치 히스토리 목록</a> | <a href="#">Open API 와이파이 정보 가져오기</a><br/>
LAT: <input type="text">, LNT: <input type="text"> <button>내 위치 가져오기</button><button>근처 WIFI 정보 보기</button>
<table>
    <thead>
    <tr>
        <td>거리(Km)</td>
        <td>관리번호</td>
        <td>자치구</td>
        <td>와이파이명</td>
        <td>도로명주소</td>
        <td>상세주소</td>
        <td>설치위치(층)</td>
        <td>설치유형</td>
        <td>설치기관</td>
        <td>서비스구분</td>
        <td>망종류</td>자
        <td>설치년도</td>
        <td>실내외구분</td>
        <td>WIFI접속환경</td>
        <td>X좌표</td>
        <td>Y좌표</td>
        <td>작업일</td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <% if(wifiList == null) { %>
        <td>
        위치 정보를 입력한 후에 조회해 주세요.
        </td>
        <%} else{  %>
        <%}%>
    </tr>
    </tbody>

</table>




<%--<%--%>
<%--    out.write("hi");--%>
<%--    MemberService memberService = new MemberService();--%>
<%--    List<Member> memberList = memberService.list();--%>
<%--    out.write(memberList.size());--%>
<%--%>--%>
<%--    <table>--%>
<%--    <%--%>
<%--    for(Member member: memberList){--%>
<%--    %>--%>
<%--    <tbody>--%>
<%--    <tr>--%>
<%--        <td><%=member.getMemberType()%> </td>--%>
<%--        <td>--%>
<%--            <a href="detail.jsp?memberType=<%=member.getMemberType()%>&userId=<%=member.getUserId()%>">--%>
<%--                <%=member.getUserId()%>--%>
<%--            </a>--%>
<%--        </td>--%>
<%--        <td><%=member.getPassword()%></td>--%>
<%--        <td><%=member.getName()%></td>--%>
<%--    </tr>--%>
<%--    </tbody>--%>
<%--    <%--%>
<%--    }--%>
<%--    %>--%>
<%--    </table>--%>

</body>
</html>