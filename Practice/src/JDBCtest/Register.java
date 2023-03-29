package JDBCtest;

import Utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 用户注册
 *      (运用JDBC中PreparedStatement和事务、回滚)
 *
 *     功能包括：
 *              1、用户注册
 *              2、用户存在检查
 *              3、账户初始化
 */
public class Register {
    public static void main(String[] args) {
        boolean flag=true;
        while(flag){
            flag=register(registerUI());
            if(flag){
                System.out.println("注册成功。");
                flag=false;
            }else {
                System.out.println("注册失败。");
                System.out.println("请重新注册......");
                flag=true;
            }
        }
        System.out.println("是否初始化账户？('是'or'否')");
        Scanner scanner=new Scanner(System.in);
        String isInitialize=scanner.next();
        if("是".equals(isInitialize)){
            boolean succeed=accountInitialize();
            System.out.println(succeed?"账户初始化成功":"账户初始化失败");
            if(succeed){
                System.out.println("账户余额10000");
            }
        }
    }

    //账户注册界面
    public static Map<String,String> registerUI(){
        System.out.println("这是注册页面。。。");
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入账号：");
        //账号
        String id=scanner.next();
        System.out.print("请输入密码：");
        //密码
        String psw=scanner.next();
        //账号密码放入Map
        Map<String,String> data=new HashMap<>(3);
        data.put("userID",id);
        data.put("password",psw);
        //返回Map
        return data;
    }

    //账号信息录入（运用了事务,一个信息录入失败则其它信息录入都失败）
    public static boolean register(Map<String,String> user){
        //记录用户输入
        String id=user.get("userID");
        String psw=user.get("password");
        //注册状态标记
        boolean flag=false;
        Connection con=null;
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        try {
            con=Utils.getConnection();
            //将自动提交机制修改为手动提交
            con.setAutoCommit(false);
            //查询账户是否存在,不存在则完成注册
            if(flag=!(isExist(user.get("userID")))){
                //录入信息
                //录入账号表t_user
                String sql1="insert into t_user(id,password) value(?,?)";
                ps1=con.prepareStatement(sql1);
                ps1.setString(1,id);
                ps1.setString(2,psw);
                ps1.executeUpdate();
                //录入信息表t_userdata
                String sql2="insert into t_userdata(id,nickname) value(?,?)";
                ps2=con.prepareStatement(sql2);
                ps2.setString(1,id);
                ps2.setString(2,"用户"+id);
                ps2.executeUpdate();
                //以上数据没有异常则手动提交
                con.commit();
                flag=true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
        }finally {
            if(ps2!=null){
                try {
                    ps2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            Utils.closeResource(con,ps1,null);
            return flag;
        }
    }

    //查询账户是否存在
    public static boolean isExist(String id){
        //注册状态标记
        boolean flag=false;
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet re=null;
        try {
            con=Utils.getConnection();
            //查询账户是否存在
            String sqlsel="select id from t_user where id=?";
            ps=con.prepareStatement(sqlsel);
            ps.setString(1,id);
            re=ps.executeQuery();
            if(re.next()) {
                flag = true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Utils.closeResource(con,ps,re);
            return flag;
        }
    }

    //创建账户
    //初始化余额10000
    public static boolean accountInitialize(){
        boolean flag=false;
        System.out.println("这是账户初始化页面。。。");
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入账号：");
        //账号
        String id=scanner.next();
        System.out.print("请输入密码：");
        //密码
        String psw=scanner.next();
        flag=isExist(id);
        if(!flag){
            return flag;
        }
        Connection con=null;
        PreparedStatement ps=null;
        try {
            con=Utils.getConnection();
            String sql="insert into t_account(id,money) value(?,?)";
            ps=con.prepareStatement(sql);
            ps.setString(1,id);
            ps.setInt(2,10000);
            ps.executeUpdate();
            flag=true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Utils.closeResource(con,ps,null);
            return flag;
        }
    }
}
