package service;

import db.Wifi;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class WifiService {
    private final String url = "jdbc:mariadb://localhost:3306/wifi";
    private final String dbUserId = "root";
    private final String dbPassword = "1234";
    private static final Logger logger = Logger.getLogger(WifiService.class.getName());
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

        if (unit.equals("kilometer")) {
            dist = dist * 1.609344;
        } else if(unit.equals("meter")){
            dist = dist * 1609.344;
        }
        return String.format("%.4f", dist);
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
            for (Wifi value : wifiList) {
                String sql = "insert into data(X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2," +
                        " X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, " +
                        "X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM)" +
                        "values (?, ?, ?, ?, ?," +
                        " ?, ?, ?, ?, ?," +
                        " ?, ?, ?, ?, ?, ?); ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, value.getX_SWIFI_MGR_NO().replace("\"", ""));
                preparedStatement.setString(2, value.getX_SWIFI_WRDOFC().replace("\"", ""));
                preparedStatement.setString(3, value.getX_SWIFI_MAIN_NM().replace("\"", ""));
                preparedStatement.setString(4, value.getX_SWIFI_ADRES1().replace("\"", ""));
                preparedStatement.setString(5, value.getX_SWIFI_ADRES2().replace("\"", ""));
                preparedStatement.setString(6, value.getX_SWIFI_INSTL_FLOOR().replace("\"", ""));
                preparedStatement.setString(7, value.getX_SWIFI_INSTL_TY().replace("\"", ""));
                preparedStatement.setString(8, value.getX_SWIFI_INSTL_MBY().replace("\"", ""));
                preparedStatement.setString(9, value.getX_SWIFI_SVC_SE().replace("\"", ""));
                preparedStatement.setString(10, value.getX_SWIFI_CMCWR().replace("\"", ""));
                preparedStatement.setString(11, value.getX_SWIFI_CNSTC_YEAR().replace("\"", ""));
                preparedStatement.setString(12, value.getX_SWIFI_INOUT_DOOR().replace("\"", ""));
                preparedStatement.setString(13, value.getX_SWIFI_REMARS3().replace("\"", ""));
                preparedStatement.setString(14, value.getLAT().replace("\"", ""));
                preparedStatement.setString(15, value.getLNT().replace("\"", ""));
                preparedStatement.setString(16, value.getWORK_DTTM().replace("\"", ""));
                int affected = preparedStatement.executeUpdate();
//                connection.commit();
                if (affected > 0) {
                    logger.info("저장 성공");
                } else {
                    logger.severe("저장 실패");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
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
    }
}
