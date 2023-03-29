package Utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtils {
    private DBUtils() {
    }

    //获取连接
    public static Connection getConnection(String url, String database, String username, String password) throws Exception {
        //记录参数
        String driver = "com.musql.jdbc.Driver";
        //2、加载驱动
        Class.forName(driver);
        //3、获取连接
        Connection con = DriverManager.getConnection(url, username, password);
        return con;
    }

    //获取连接(从配置文件加载)
    public static Connection getConnection() throws Exception {
        //加载配置文件
        //相对路径起点在当前模块下
        InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("Utils//jdbc.properties");
        //ResourceBundle rb=ResourceBundle.getBundle("JDBC//src//JDBC//jdbc.properties");
        Properties pro=new Properties();
        //1、获取配置资源中的四个信息
        pro.load(is);
        String driver=pro.getProperty("driver");
        String url=pro.getProperty("url");
        String user=pro.getProperty("user");
        String password=pro.getProperty("password");
        //2、加载驱动
        Class.forName(driver);
        //3、获取连接
        Connection con= DriverManager.getConnection(url,user,password);
        return con;
    }

    //关闭连接
    public static void closeConnection(Connection con, Statement st){
        if(st!=null){
            try {
                st.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(con!=null){
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //数据库数据修改
    public static int setUpdate(String sql, String... args) {
        //获取连接
        Connection con=null;
        PreparedStatement ps=null;
        int count = 0;
        try {
            con = getConnection();
            ps=con.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                ps.setString(i+1,args[i]);
            }
            count=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection(con,ps);
        }
        return count;
    }
}
