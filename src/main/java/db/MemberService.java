package db;

import servlet.SaveWifiList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MemberService {
    private static final Logger logger = Logger.getLogger(MemberService.class.getName());

    public List<Member> list(){
        /**
         * 1. ip(domain)
         * 2. port
         * 3. 계정
         * 4. 패스워드
         * 5. 인스턴스
         */
        List<Member> memberList = new ArrayList<>();

        String url = "jdbc:mariadb://localhost:3306/test3";
        String dbUserId = "root";
        String dbPassword = "1234";
        /**
         * 1. 드라이버 로드
         * 2. 커넥션 객체 생성
         * 3. 스테이트먼트 객체 생성
         * 4. 쿼리 실행
         * 5. 결과 수행
         * 6. 객체 연결 해제(close)
         */

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
//        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String memberTypeValue = "email";
//        String memberTypeValue = "email' or 1 = 1'";


        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            String sql = "select member_type, user_id, password, name" +
                    " from member " +
                    " where member_type = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberTypeValue);

            rs = preparedStatement.executeQuery();

            while(rs.next()){
                String memberType = rs.getString("member_type");
                String userId = rs.getString("user_id");
                String password = rs.getString("password");
                String name = rs.getString("name");

                Member member = new Member();
                member.setMemberType(memberTypeValue);
                member.setUserId(userId);
                member.setPassword(password);
                member.setName(name);
                memberList.add(member);
                System.out.println(memberType +", " + userId+", " + password+", "+name);
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
        return memberList;
    }
    public Member detail(String p_memberType, String p_userId){
        /**
         * 1. ip(domain)
         * 2. port
         * 3. 계정
         * 4. 패스워드
         * 5. 인스턴스
         */
        Member member = null;

        String url = "jdbc:mariadb://localhost:3306/test3";
        String dbUserId = "root";
        String dbPassword = "1234";
        /**
         * 1. 드라이버 로드
         * 2. 커넥션 객체 생성
         * 3. 스테이트먼트 객체 생성
         * 4. 쿼리 실행
         * 5. 결과 수행
         * 6. 객체 연결 해제(close)
         */

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
//        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

//        String memberTypeValue = "email' or 1 = 1'";


        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            String sql = "select member_type, user_id, password, name" +
                    " from member " +
                    " where member_type = ? and user_Id = ? ";
//            String sql2 = "select m.member_type, m.user_id, m.password, m.name "
//                    + " , md.mobile_no "
//                    + " , md.marketing_yn "
//                    + " , md.register_data "
//                    + " from member m "
//                    + "     left join member_detail md on md.member_type = m.member_type and m.user_id = md.user_id "
//                    + " where m.member_type = ? and m.user_id = ? ";


            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, p_memberType);
            preparedStatement.setString(2, p_userId);

            rs = preparedStatement.executeQuery();

            if(rs.next()){
                member = new Member();
                String memberType = rs.getString("member_type");
                String userId = rs.getString("user_id");
                String password = rs.getString("password");
                String name = rs.getString("name");

                member.setMemberType(memberType);
                member.setUserId(userId);
                member.setPassword(password);
                member.setName(name);
                System.out.println(memberType +", " + userId+", " + password+", "+name);
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
        return member;
    }
    public void register(Member member){
        String url = "jdbc:mariadb://localhost:3306/test3";
        String dbUserId = "root";
        String dbPassword = "1234";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
//        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String memberTypeValue = member.getMemberType();
//        String memberTypeValue = "email' or 1 = 1'";
        String userIdValue = member.getUserId();
        String passwordValue = member.getPassword();
        String nameValue = member.getName();

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            String sql = "insert into member(member_type, user_id, password, name)"+
                    "values (?, ?, ? , ?); ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberTypeValue);
            preparedStatement.setString(2, userIdValue);
            preparedStatement.setString(3, passwordValue);
            preparedStatement.setString(4, nameValue);

            int affected = preparedStatement.executeUpdate();
            if( affected > 0){
                logger.info("저장 성공");
            }else{
                logger.severe("저장 실패");
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
    public void dbUpdate(){
        String url = "jdbc:mariadb://localhost:3306/test3";
        String dbUserId = "root";
        String dbPassword = "1234";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
//        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String memberTypeValue = "email";
//        String memberTypeValue = "email' or 1 = 1'";
        String userIdValue = "zerobase@naver.com";
        String passwordValue = "9999";

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            String sql = "update member set " +
                    " password = ? " +
                    " where member_type = ? and user_id = ? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, passwordValue);
            preparedStatement.setString(2, memberTypeValue);
            preparedStatement.setString(3, userIdValue);

            int affected = preparedStatement.executeUpdate();
            if( affected > 0){
                logger.info("수정 성공");
            }else{
                logger.severe("수정 실패");
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
    public void withdraw(Member member){
        String url = "jdbc:mariadb://localhost:3306/test3";
        String dbUserId = "root";
        String dbPassword = "1234";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
//        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

//        String memberTypeValue = "email' or 1 = 1'";

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            String sql = "delete from member " +
                    " where member_type = ? and user_id = ? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemberType());
            preparedStatement.setString(2, member.getUserId());

            int affected = preparedStatement.executeUpdate();
            if( affected > 0){
                logger.info("삭제 성공");
            }else{
                logger.severe("삭제 실패");
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
