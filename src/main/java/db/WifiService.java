package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiService {
    private String url = "jdbc:mariadb://localhost:3306/wifi";
    private String dbUserId = "root";
    private String dbPassword = "1234";

    public List<Wifi> list(){
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
                wifi.setLAT(rs.getString("LAT"));
                wifi.setLNT(rs.getString("LNT"));
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
    public void register(List<Wifi> wifiList){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            for(int i=0; i<wifiList.size(); i++) {
                Wifi wifi = new Wifi();
                wifi = wifiList.get(i);
                connection = DriverManager.getConnection(url, dbUserId, dbPassword);
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
                preparedStatement.setString(14, wifi.getLAT());
                preparedStatement.setString(15, wifi.getLNT());
                preparedStatement.setString(16, wifi.getWORK_DTTM());

                int affected = preparedStatement.executeUpdate();
                if (affected > 0) {
                    System.out.println("저장 성공");
                } else {
                    System.out.println("저장 실패");
                }
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
    }
}
