package JDBCtest;
import java.sql.*;
public class JDBCtest3 {
    public static void main(String[] args) {
        Connection con = null;
        Statement stm = null;
        try {
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bjpowernode?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai", "root", "2019");
            //获取操作对象
            Statement sta = con.createStatement();
            //执行SQL
            int count = sta.executeUpdate("insert into dept(deptno,dname,loc) value(60,'憨憨','昆明')");
            System.out.println(count == 1 ? "插入成功" : "插入失败");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (stm != null) {
                    try {
                        stm.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}