package JDBCtest;

import Utils.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * JDBC事务机制:
 *          1、JDBC中的事务是自动提交的，既只要执行一条DML语句，就提交一次数据。
 *              自动提交JDBC默认的。
 *              但在业务中，通常都是N条DML语句共同联合完成。
 *              必须保证在同一个事务中，要么同时成功，要么同时失败。
 *
 *             验证自动提交机制（略）。
 *          2、用Connection中的setAutoCommit()方法可以禁用自动提交。
 *             默认是true，自动提交，改为false，手动提交
 *              等所有事务处理完成，手动调用Connection中的commit()方法，提交。
 *
 */
public class TransferAccount {
    //转账测试
    public static void main(String[] args) {
        //转账
        Connection con=null;
        PreparedStatement ps=null;
        try {
            con= Utils.getConnection();
            con.setAutoCommit(false);
            String sql="update t_account set money=? where id=?";
            ps=con.prepareStatement(sql);
            ps.setInt(1,8000);
            ps.setString(2,"2019");
            ps.executeUpdate();
            ps.setInt(1,12000);
            ps.setString(2,"2018");
            ps.executeUpdate();
            con.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            Utils.closeResource(con,ps,null);
        }
    }
}
