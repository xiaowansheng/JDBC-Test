package JDBCtest;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * 实现需求：
 *      模拟用户登录功能
 *      从数据库中的用户名和密码判断输入是否正确来判断登录。
 *
 *
 * 存在问题：
 *          用Statement来执行SQL查询语句，存在安全问题。
 *          如：select id,password from t_user where id='"+id+"'and"+password='"+psw+"'";
 *          拼接的SQL查询语句会存在SQL注入（安全隐患）。
 *
 * 问题原因：
 *          用Statement来处理SQL语句，语句编译一次执行一次，用户输入信息也参与编译。
 *          用户输入的信息中包含SQL语句的关键字，且这些关键字参与SQL的编译过程，
 *          导致原本的SQL语句的原意被扭曲，进而达到被SQL注入。
 *
 * 解决问题：
 *          只要用户提供的信息不参与SQL语句的编译过程，问题就解决了。
 *          即使用户提供的信息包含SQL关键字，但是不参与编译，对原语句没有作用。
 *          要想用户信息不参与SQL语句的编译，那就必须使用java.sql.PreparedStatement
 *          PreparedStatement接口继承了java.sql.Statement
 *          属于预编译的操作对象。
 *          ParedStatement的原理是：预先对SQL语句的框架进行编译，再给SQL语句传‘值’来操作。
 *
 *  Statement和PreparedStatement对比：
 *              Statement存在SQL注入问题，PreparedStatement解决了这个问题。
 *              Statement是编译一次执行一次，PrepareStatement是编译一次，执行n次，所以后者效率高。
 *              PreparedStatement会在编译阶段做类型的安全检查。
 *
 *              综上所述，PreparedStatement使用较多，Statement使用较少（有些特殊业务需要SQL注入）。
 */
public class LogIn {
    public static void main(String[] args) {
        Map<String,String> data=UI();
        boolean loginSucceed=login(data);
        System.out.println(loginSucceed?"登录成功":"登录失败");
    }

    private static boolean login(Map<String, String> data) {
        //登录状态标记
        boolean flag=false;
        //账号密码
        String id=data.get("userID");
        String psw=data.get("password");
        Connection con=null;
        PreparedStatement pst=null;
        ResultSet re=null;
        try {
            //1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2、连接数据库
            con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai","root","2019");
            //3、获取数据库操作对象,预编译SQL
            //？是占位符，一个？一个值，编译后这个位置用来填值。
            String sql="select id,password from t_user where id=? and password=?";
            pst=con.prepareStatement(sql);
            //上面这一步，先把sql传给DBMS进行sql的预先编译。
            //4、执行SQL
            //给？传值（序号是从1开始），直接执行。（上面已经编译过了，可以直接执行。）
            pst.setString(1,id);
            pst.setString(2,psw);
            //对传入的值进行查询
            re=pst.executeQuery();
            //5、处理查询结果集
            if(re.next()){
                flag=true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            if(pst!=null){
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(con!=null){
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(re!=null){
                try {
                    re.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //返回密码验证情况
            return flag;
        }
    }

    //用户登录界面
    private static Map<String,String> UI() {
        System.out.println("这是登录界面。。。");
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入账号：");
        //账号
        String id=scanner.next();
        System.out.print("请输入密码：");
        //密码
        String psw=scanner.next();
        //账号密码放入Map
        Map<String,String> data=new HashMap<>(2);
        data.put("userID",id);
        data.put("password",psw);
        //返回Map
        return data;
    }
}
