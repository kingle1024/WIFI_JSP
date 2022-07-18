<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List" %>

<%@ page import="db.Wifi" %>
<%@ page import="service.WifiService" %>
<%
    WifiService wifiService = new WifiService();
    List<Wifi> wifiList = wifiService.list();
%>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
    $(document).ready(function(){
        $('#ajaxToGet').click(function(){
            var lat = document.getElementById("LAT").value;
            var lnt = document.getElementById("LNT").value;
            $.ajax({
                type : "GET",
                url : "${pageContext.request.contextPath}/getWIFI?LAT="+lat+"&LNT="+lnt,
                success: function(data){
                    var d = JSON.parse(data);
                    var table = "";
                    var lat1 = document.getElementById("LAT").value;
                    var lnt1 = document.getElementById("LNT").value;
                    for(key in d){
                        table += "<tr>" +
                            "<td>"+distance(lat1, lnt1, d[key].LAT, d[key].LNT, "kilometer")+"</td>" +
                            "<td>"+d[key].X_SWIFI_MGR_NO+"</td>" +
                            "<td>"+d[key].X_SWIFI_WRDOFC+"</td>" +
                            "<td>"+d[key].X_SWIFI_MAIN_NM+"</td>" +
                            "<td>"+d[key].X_SWIFI_ADRES1+"</td>" +
                            "<td>"+d[key].X_SWIFI_ADRES2+"</td>" +
                            "<td>"+d[key].X_SWIFI_INSTL_FLOOR+"</td>" +
                            "<td>"+d[key].X_SWIFI_INSTL_TY+"</td>" +
                            "<td>"+d[key].X_SWIFI_INSTL_MBY+"</td>" +
                            "<td>"+d[key].X_SWIFI_SVC_SE+"</td>" +
                            "<td>"+d[key].X_SWIFI_CMCWR+"</td>" +
                            "<td>"+d[key].X_SWIFI_CNSTC_YEAR+"</td>" +
                            "<td>"+d[key].X_SWIFI_INOUT_DOOR+"</td>" +
                            "<td>"+d[key].X_SWIFI_REMARS3+"</td>" +
                            "<td>"+d[key].LAT+"</td>" +
                            "<td>"+d[key].LNT+"</td>" +
                            "<td>"+d[key].WORK_DTTM+"</td>" +
                            "</tr>";
                    }

                    document.getElementById("resultBody").innerHTML += table;
                },
                error : function(request, status, error){
                    console.log('code+request.status'+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
                    // alert('code+request.status'+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
                }
            });
        });
    });
</script>
<script>
    function distance(lat1, lon1, lat2, lon2, unit){
        lon1 = parseFloat(lon1);
        lon2 = parseFloat(lon2);
        lat1 = parseFloat(lat1);
        lat2 = parseFloat(lat2);

        var theta = lon1 - lon2;
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }
    function deg2rad(deg){
        deg = parseFloat(deg);
        return (deg * Math.PI / 180.0);
    }
    function rad2deg(rad){
        rad = parseFloat(rad);
        return (rad * 180 / Math.PI);
    }

    function getLocation(){
        // BOM의 navigator객체의 하위에 geolocation객체가 새로 추가되었음.
        window.navigator.geolocation.getCurrentPosition( function(position){ //OK
                var lat= position.coords.latitude;
                var lng= position.coords.longitude;
                // document.getElementById('target').innerHTML=;
                document.getElementById('LAT').value = lat;
                document.getElementById('LNT').value = lng;
                console.log(lat+", "+lng);
            } ,
            function(error){ //error
                switch(error.code){
                    case error.PERMISSION_DENIED:
                        str="사용자 거부";
                        break;
                    case error.POSITION_UNAVAILABLE:
                        str="지리정보 없음";
                        break;
                    case error.TIMEOUT:
                        str="시간 초과";
                        break;
                    case error.UNKNOWN_ERROR:
                        str="알수없는 에러";
                        break;
                }
                document.getElementById('LAT').innerHTML=str;
                document.getElementById('LNT').innerHTML=str;
            });
    }

</script>
<body>
<h1>와이파이 정보 구하기
</h1>
<br/>
<a href="#">홈</a> | <a href="#">위치 히스토리 목록</a> | <a href="/getWIFI">Open API 와이파이 정보 가져오기</a><br/>
LAT: <input type="text" id="LAT">, LNT: <input type="text" id="LNT"> <button onclick="getLocation()">내 위치 가져오기</button><button id="ajaxToGet">근처 WIFI 정보 보기</button>
<table id="wifiData">
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
        <td>망종류</td>
        <td>설치년도</td>
        <td>실내외구분</td>
        <td>WIFI접속환경</td>
        <td>X좌표</td>
        <td>Y좌표</td>
        <td>작업일</td>
    </tr>
    </thead>
    <tbody id="resultBody">
<%--        <% if(wifiList == null) { %>--%>
<%--        <tr>--%>
<%--        <td>--%>
<%--        위치 정보를 입력한 후에 조회해 주세요.--%>
<%--        </td>--%>
<%--        </tr>--%>
<%--        <%} else{  %>--%>

<%--        <% for(Wifi wifi: wifiList){ %>--%>

<%--        <tr>--%>
<%--            <td><script>check(<%=wifi.getLAT()%>)</script></td>--%>
<%--            <td><%=wifi.getX_SWIFI_MGR_NO()%></td>--%>
<%--            <td><%=wifi.getX_SWIFI_WRDOFC()%></td>--%>
<%--            <td><%=wifi.getX_SWIFI_MAIN_NM()%></td>--%>
<%--            <td><%=wifi.getX_SWIFI_ADRES1()%></td>--%>
<%--            <td><%=wifi.getX_SWIFI_ADRES2()%></td>--%>
<%--            <td><%=wifi.getX_SWIFI_INSTL_FLOOR()%></td>--%>
<%--            <td><%=wifi.getX_SWIFI_INSTL_TY()%></td>--%>
<%--            <td><%=wifi.getX_SWIFI_INSTL_MBY()%></td>--%>
<%--            <td><%=wifi.getX_SWIFI_SVC_SE()%></td>--%>
<%--            <td><%=wifi.getX_SWIFI_CMCWR()%></td>--%>
<%--            <td><%=wifi.getX_SWIFI_CNSTC_YEAR()%></td>--%>
<%--            <td><%=wifi.getX_SWIFI_INOUT_DOOR()%></td>--%>
<%--            <td><%=wifi.getX_SWIFI_REMARS3()%></td>--%>
<%--            <td><%=wifi.getLAT()%></td>--%>
<%--            <td><%=wifi.getLNT()%></td>--%>
<%--            <td><%=wifi.getWORK_DTTM()%></td>--%>
<%--        </tr>--%>
<%--        <% } %>--%>

<%--        <%}%>--%>
<%--    </tr>--%>
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