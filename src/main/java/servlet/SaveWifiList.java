package servlet;

import com.google.gson.*;


import db.Wifi;
import service.WifiService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "setWIFI", value = "/setWIFI")
public class SaveWifiList extends HttpServlet {
    private String url = "http://openapi.seoul.go.kr:8088/794f684f4b6b696e36304c486f4970/json/TbPublicWifiInfo";

    public void init() {
        String message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        String getTotalCount = getWifiTotalCount(url);
        JsonObject json_totalCount = new Gson().fromJson(getTotalCount, JsonObject.class);
        int totalCount = Integer.parseInt(json_totalCount.getAsJsonObject("TbPublicWifiInfo").get("list_total_count").toString());

        try {
            int resultCount = setWifi(totalCount);
            PrintWriter out = response.getWriter();
            out.println("<center><h1>"+resultCount+"개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>");
            out.println("<a href='./list.jsp'>홈 으로 가기</a></center>");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
    String getWifiTotalCount(String url){
        String result = null;
        try{
            OkHttpClient client = new OkHttpClient();
            url = url+"/1/1/";
            Request rq = new Request.Builder()
                    .url(url)
                    .build();
            Response rs = client.newCall(rq).execute();
            if (rs.body() != null) {
                result = rs.body().string();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    String getWifiLow(String startIdx){
        String result = null;
        try{
            OkHttpClient client = new OkHttpClient();
            url = url+"/"+startIdx+"/100/";
            Request rq = new Request.Builder()
                    .url(url)
                    .build();
            Response rs = client.newCall(rq).execute();
            if (rs.body() != null) {
                result = rs.body().string();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    int setWifi(int totalCount) throws InterruptedException {
        List<Wifi> wifiList = new ArrayList<>();
        WifiService wifiService = new WifiService();
        for(int i=0; i < (totalCount/100)+1; i++) {
            JsonArray jsonArray = new Gson().fromJson(getWifiLow(String.valueOf(i)), JsonObject.class).getAsJsonObject("TbPublicWifiInfo").getAsJsonArray("row");
            for (JsonElement j : jsonArray) {
                Wifi wifi = new Wifi();
                wifi.setX_SWIFI_MGR_NO(String.valueOf(j.getAsJsonObject().get("X_SWIFI_MGR_NO"))); // 관리번호
                wifi.setX_SWIFI_WRDOFC(String.valueOf(j.getAsJsonObject().get("X_SWIFI_WRDOFC"))); // 자치구
                wifi.setX_SWIFI_MAIN_NM(String.valueOf(j.getAsJsonObject().get("X_SWIFI_MAIN_NM"))); // 와이파이명
                wifi.setX_SWIFI_ADRES1(String.valueOf(j.getAsJsonObject().get("X_SWIFI_ADRES1"))); // 도로명주소
                wifi.setX_SWIFI_ADRES2(String.valueOf(j.getAsJsonObject().get("X_SWIFI_ADRES2"))); // 상세주소
                wifi.setX_SWIFI_INSTL_FLOOR(String.valueOf(j.getAsJsonObject().get("X_SWIFI_INSTL_FLOOR"))); // 설치위치(층)
                wifi.setX_SWIFI_INSTL_TY(String.valueOf(j.getAsJsonObject().get("X_SWIFI_INSTL_TY"))); // 설치유형
                wifi.setX_SWIFI_INSTL_MBY(String.valueOf(j.getAsJsonObject().get("X_SWIFI_INSTL_MBY"))); // 설치기관
                wifi.setX_SWIFI_SVC_SE(String.valueOf(j.getAsJsonObject().get("X_SWIFI_SVC_SE"))); // 서비스구분
                wifi.setX_SWIFI_CMCWR(String.valueOf(j.getAsJsonObject().get("X_SWIFI_CMCWR"))); // 망종류
                wifi.setX_SWIFI_CNSTC_YEAR(String.valueOf(j.getAsJsonObject().get("X_SWIFI_CNSTC_YEAR"))); // 설치년도
                wifi.setX_SWIFI_INOUT_DOOR(String.valueOf(j.getAsJsonObject().get("X_SWIFI_INOUT_DOOR"))); // 실내외구분
                wifi.setX_SWIFI_REMARS3(String.valueOf(j.getAsJsonObject().get("X_SWIFI_REMARS3"))); // WIFI접속환경
                wifi.setLAT(String.valueOf(j.getAsJsonObject().get("LAT"))); // X좌표
                wifi.setLNT(String.valueOf(j.getAsJsonObject().get("LNT"))); // Y좌표
                wifi.setWORK_DTTM(String.valueOf(j.getAsJsonObject().get("WORK_DTTM"))); // 작업일자
                wifiList.add(wifi);
            }
        }
        wifiService.register(wifiList);
        return wifiList.size();
    }
}