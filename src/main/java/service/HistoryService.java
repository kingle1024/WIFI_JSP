package service;

import db.History;
import db.Member;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HistoryService {
    private String url = "jdbc:mariadb://localhost:3306/wifi";
    private String dbUserId = "root";
    private String dbPassword = "1234";
    private static Logger logger = Logger.getLogger(HistoryService.class.getName());
    public List<History> list(){
        List<History> historyList = new ArrayList<>();

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
                    " from history order by id desc";
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while(rs.next()){
                History history = new History();
                history.setLAT(rs.getString("lat"));
                history.setLNT(rs.getString("lnt"));
                history.setDate(sdf.format(rs.getTimestamp("registerDate")));
                historyList.add(history);
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
        return historyList;
    }


}
