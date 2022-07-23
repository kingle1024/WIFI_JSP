package service;

import db.Wifi;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class WifiService {
    private String url = "jdbc:mariadb://localhost:3306/wifi";
    private String dbUserId = "root";
    private String dbPassword = "1234";
    private static Logger logger = Logger.getLogger(WifiService.class.getName());
    public List<Wifi> list(HashMap<String, String> map){
        List<Wifi> wifiList = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            String sql = "select * " +
                    " from data ";
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Wifi wifi = new Wifi();
                String LNT = String.valueOf(rs.getString("LNT")).replace("\"", "");
                String LAT = String.valueOf(rs.getString("LAT")).replace("\"", "");
//                wifi.setKm(distance(Double.parseDouble(map.get("LAT")), Double.parseDouble(map.get("LNT")), Double.parseDouble(LNT), Double.parseDouble(LAT), "kilometer"));
                wifi.setKm(Double.parseDouble(distance(Double.parseDouble(map.get("LAT")), Double.parseDouble(map.get("LNT")), Double.parseDouble(LNT), Double.parseDouble(LAT), "kilometer")));
                wifi.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
                wifi.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
                wifi.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
                wifi.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
                wifi.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
                wifi.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
                wifi.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
                wifi.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
                wifi.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
                wifi.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
                wifi.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
                wifi.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
                wifi.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
                wifi.setLAT(LAT);
                wifi.setLNT(LNT);
                wifi.setWORK_DTTM(rs.getString("WORK_DTTM"));

                wifiList.add(wifi);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            try {
                if(rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if(preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return wifiList;
    }
    static String distance(double lat1, double lon1, double lat2, double lon2, String unit){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return String.valueOf(dist);
    }
    static double deg2rad(double deg){
        return (deg * Math.PI / 180.0);
    }
    static double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }
    public void register(List<Wifi> wifiList) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        connection.setAutoCommit(false);
        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            for(int i=0; i<wifiList.size(); i++) {
                Wifi wifi = new Wifi();
                wifi = wifiList.get(i);
                String sql = "insert into data(X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2," +
                        " X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, " +
                        "X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM)" +
                        "values (?, ?, ?, ?, ?," +
                        " ?, ?, ?, ?, ?," +
                        " ?, ?, ?, ?, ?, ?); ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, wifi.getX_SWIFI_MGR_NO());
                preparedStatement.setString(2, wifi.getX_SWIFI_WRDOFC());
                preparedStatement.setString(3, wifi.getX_SWIFI_MAIN_NM());
                preparedStatement.setString(4, wifi.getX_SWIFI_ADRES1());
                preparedStatement.setString(5, wifi.getX_SWIFI_ADRES2());
                preparedStatement.setString(6, wifi.getX_SWIFI_INSTL_FLOOR());
                preparedStatement.setString(7, wifi.getX_SWIFI_INSTL_TY());
                preparedStatement.setString(8, wifi.getX_SWIFI_INSTL_MBY());
                preparedStatement.setString(9, wifi.getX_SWIFI_SVC_SE());
                preparedStatement.setString(10, wifi.getX_SWIFI_CMCWR());
                preparedStatement.setString(11, wifi.getX_SWIFI_CNSTC_YEAR());
                preparedStatement.setString(12, wifi.getX_SWIFI_INOUT_DOOR());
                preparedStatement.setString(13, wifi.getX_SWIFI_REMARS3());
                preparedStatement.setString(14, wifi.getLAT().replace("\"", ""));
                preparedStatement.setString(15, wifi.getLNT().replace("\"", ""));
                preparedStatement.setString(16, wifi.getWORK_DTTM());
                int affected = preparedStatement.executeUpdate();
//                connection.commit();
                if (affected > 0) {
                    System.out.println("저장 성공");
                } else {
                    System.out.println("저장 실패");
                }
            }
        } catch (SQLException e) {
            System.out.println("runtime err :"+e);
            throw new RuntimeException(e);
        }finally{
            try {
                if(preparedStatement != null && !preparedStatement.isClosed()){
                    System.out.println("prepared close");
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println("pre err" + e);
                throw new RuntimeException(e);
            }
            try {
                if(connection != null && !connection.isClosed()){
                    System.out.println("connected close");
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("conn err " +e);
                throw new RuntimeException(e);
            }
        }
    }
}
