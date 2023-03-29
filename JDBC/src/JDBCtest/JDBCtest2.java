package JDBCtest;
import java.sql.*;
public class JDBCtest2
{
    public static void main(String[] args)
    {
        Connection con=null;
        Statement state=null;
        try{
            //1、注册驱动
            /**类加载完成注册(看JDBC源码)**/
            Class.forName("com.mysql.jdbc.Driver");
            //2、获取连接
            con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bjpowernode?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai","root","2019");
            System.out.println("连接对象："+con);
        }catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            //6、释放资源
            if(con!=null){
                try{
                    con.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
