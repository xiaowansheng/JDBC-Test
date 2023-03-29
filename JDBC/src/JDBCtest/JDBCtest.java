package JDBCtest;

import java.sql.*;


/**
 *
 * 编程六步：
 *      1、注册驱动
 *      2、获取连接
 *      3、获取数据库操作对象
 *      4、执行SQL
 *      5、处理查询结果集
 *      6、释放资源
 */
public class JDBCtest {
    public static void main(String[] args) {
        Connection connection=null;
        Statement statement =null;
        try {
            //1、注册驱动
            Driver driver = new com.mysql.jdbc.Driver();//多态，父类型引用指向子类型对象。
            DriverManager.registerDriver(driver);

            //2、获取连接
            //URL：统一资源定位符
            //String URL="jdbc:mysql://ip:port/...";
            //MySQL8.0.11之后连接数据库时区报错问题：
            // 解决方案1:修改URL
            // 解决方案2：修改数据库时区
            String url="jdbc:mysql://127.0.0.1:3306/bjpowernode?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai";
            String user="root";
            String password="2019";
            connection=DriverManager.getConnection(url,user,password);
            //数据库连接对象：com.mysql.cj.jdbc.ConnectionImpl@57d7f8ca
            System.out.println("数据库连接对象："+connection);

            //3、获取数据库操作对象（Statement，专门执行SQL语句的）
            statement = connection.createStatement();

            //4、执行SQL
            String sql="insert into dept(deptno,dname,loc) value(50,'人事部','昆明')";
            //专门执行DML语句的（insert update delete）
            //返回值是影响数据库中的记录条数
            int count = statement.executeUpdate(sql);
            System.out.println(count==1?"保存成功！":"保存失败！");

            //5、处理查询结果集
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //6、释放资源
            //为了保证资源一定释放，在finally中关闭资源
            //遵循从小到大依次关闭
            //并分别对其try。。。catch。。。
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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

    }
}
