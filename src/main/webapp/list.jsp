<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List" %>

<%@ page import="db.Wifi" %>
<%@ page import="service.WifiService" %>

<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<style>
    .table thead th{
        background-color : #32b584;
        color : white;
    }
</style>
<script>
    $(document).ready(function(){
        $('#ajaxToGet').click(function(){
            var lat = document.getElementById("LAT").value;
            var lnt = document.getElementById("LNT").value;
            if(lat === "" || lnt === ""){
                alert("위치 정보를 입력해주세요.");
                return;
            }
            $.ajax({
                type : "GET",
                url : "${pageContext.request.contextPath}/getWIFI?LAT="+lat+"&LNT="+lnt,
                success: function(data){
                    $('tbody tr').eq(0).remove();
                    var d = JSON.parse(data);
                    var table = "";
                    for(key in d){
                        table += "<tr>" +
                            "<td>"+d[key].km+"</td>" +
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
                            "<td>"+d[key].LNT+"</td>" +
                            "<td>"+d[key].LAT+"</td>" +
                            "<td>"+d[key].WORK_DTTM+"</td>" +
                            "</tr>";
                    }

                    document.getElementById("resultBody").innerHTML += table;
                },
                error : function(request, status, error){
                    console.log('code request.status'+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
                    alert('와이파이 정보가 없습니다.')
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

        if (unit === "kilometer") {
            dist = dist * 1.609344;
        } else if(unit === "meter"){
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
<h1>와이파이 정보 구하기</h1>
<jsp:include page="commonMenu.jsp" flush="false" />

<label>LAT: <input type="text" id="LAT" ></label>
<label>, LNT: <input type="text" id="LNT"></label>
    <button onclick="getLocation()">내 위치 가져오기</button>
    <button id="ajaxToGet">근처 WIFI 정보 보기</button>

<table id="wifiData" class="table table-striped">
    <thead>
    <tr>
        <th scope="col">거리(Km)</th>
        <th scope="col">관리번호</th>
        <th scope="col">자치구</th>
        <th scope="col">와이파이명</th>
        <th scope="col">도로명주소</th>
        <th scope="col">상세주소</th>
        <th scope="col">설치위치(층)</th>
        <th scope="col">설치유형</th>
        <th scope="col">설치기관</th>
        <th scope="col">서비스구분</th>
        <th scope="col">망종류</th>
        <th scope="col">설치년도</th>
        <th scope="col">실내외구분</th>
        <th scope="col">WIFI접속환경</th>
        <th scope="col">X좌표</th>
        <th scope="col">Y좌표</th>
        <th scope="col">작업일</th>
    </tr>
    </thead>
    <tbody id="resultBody">
    <tr>
        <td colspan="17">
            <center>위치 정보를 입력한 후에 조회해 주세요.</center>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>