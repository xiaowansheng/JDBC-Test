package JDBCtest;
import java.sql.*;
public class JDBCtest1
{
    public static void main(String[] args)
    {
        Connection con=null;
        Statement state=null;
        try{
            //1、注册驱动
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //2、获取连接
            con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bjpowernode?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai","root","2019");
            //3、获取数据库操作对象
            state=con.createStatement();
            //4、执行SQL语句
            String sql="delete from dept where deptno=50";
            int count=state.executeUpdate(sql);
            System.out.println(count==1?"删除成功":"删除失败");
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            //6、释放资源
            if(state!=null){
                try{
                    state.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
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
