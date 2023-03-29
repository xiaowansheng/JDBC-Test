package Utils;

import java.sql.*;

public class Utils {
    private Utils(){}

    //注册驱动，获取数据库连接对象Connection
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        //注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //连接数据库
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai","root","2019");
    }

    //关闭资源
    public static void closeResource(Connection connection, Statement statement,ResultSet resultSet){
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
