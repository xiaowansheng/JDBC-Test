package JDBCtest;

import java.sql.*;

public class JDBCtest5 {
    public static void main(String[] args) {
        Connection con=null;
        Statement stm=null;
        ResultSet res=null;
        try{
            //1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2、连接数据库
            con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bjpowernode?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai","root","2019");
            //3、获取数据库操作对象
            stm=con.createStatement();
            //4、执行SQL语句
            /**
             * int executeUpdate(insert/update/delete)
             * ResultSet executeQuery(select)
             * */
            String sql="select * from dept";//专门执行查询语句
            res = stm.executeQuery(sql);
            //5、处理查询结果集
            //指向下一行，返回boolean,有则true
            //boolean flag=res.next();第一次指向第一行数据
            //指向的行有数据，则读数据
            while(res.next()){
                //getString()方法的特点，不管数据库中数据是什么类型，都以String形式取出。
                //不健壮
//                String deptno=res.getString(1);//参数是列的下标，从1开始。（不是0开始）
//                String dname=res.getString(2);
//                String loc=res.getString(3);

                //还可以以特定类型取出
                int deptno=res.getInt("deptno");//参数是列名，以列名获取（是查询结果的列名）
                String dname=res.getString("dname");
                String loc=res.getString("loc");
                System.out.println("deptno="+deptno+" dname="+dname+" loc="+loc);
            }
        }catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(res!=null){
                try {
                    res.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stm!=null){
                try {
                    stm.close();
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
