package JDBCtest;

import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class JDBCtest4 {
    public static void main(String[] args) {
        //使用资源绑定器绑定属性配置文件
        ResourceBundle bundle=ResourceBundle.getBundle("D:\\java\\IDEACommunity-Project\\MySQL\\JDBC\\src\\JDBCtest\\jdbc");
        String driver=bundle.getString("driver");
        String url=bundle.getString("url");
        String user=bundle.getString("user");
        String password=bundle.getString("password");
        String sql=bundle.getString("sql");
        Connection con=null;
        Statement sta=null;
        try{
            //1、注册驱动
            Class.forName(driver);
            //2、获取连接
            con= DriverManager.getConnection(url,user,password);
            //3、获取数据库操作对象
            sta=con.createStatement();
            //4、执行SQL语句
            int count=sta.getUpdateCount();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //6、关闭资源
            if(sta!=null){
                try {
                    sta.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(con!=null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
