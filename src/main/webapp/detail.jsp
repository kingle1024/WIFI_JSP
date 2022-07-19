<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List" %>
<%@ page import="db.History" %>
<%@ page import="service.HistoryService" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
    $(document).on("click", "button.delete-history-item", function(){
        var index = $(this).attr("data-index");
        if(confirm("선택하신 기록을 삭제 하시겠습니까?")){
            var itemList = [];
            itemList[0] = index;
            delHistoryItem(itemList);
        }
    })

    function delHistoryItem(itemList){
        console.log(itemList);
        $.ajax({
            url : '${pageContext.request.contextPath}/deleteHistory?id='+itemList,
            type : "GET",
            async: false,
            error : function(request, status, error){
                console.log("Code:"+request.status+" / Message:"+request.responseText+" / Error: "+error);
            },
            success: function(data){
                //console.log(data);
                if(data == 1){
                    window.alert("삭제가 완료되었습니다.");
                    document.location.reload(true);
                }else{
                    window.alert('삭제 실패');
                }
            },
            beforeSend:function(){
                $('.wrap-loading').removeClass('display-none');
                $('.wrap-loading .loading-message').html("...삭제 처리중...");
            },
            complete:function(){
                $('.wrap-loading').addClass('display-none');
                $('.wrap-loading .loading-message').html("...데이터를 저장하는 중입니다...");
            },
        })
    }
</script>
<body>
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
            <td>
                <button class="btn btn-danger btn-xs delete-history-item" data-index="<%=h.getId()%>">
                <span class="glyphicon glyphicon-trash" style=" vertical-align: middle;">삭제</span>
                </button>
            </td>

        </tr>
        <%}%>

        </tbody>
    </table>
    <div>
        <a href="./list.jsp"> list </a>
    </div>
</body>
</html>