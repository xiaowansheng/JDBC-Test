package Utils;

import org.junit.Test;

import java.io.File;
import java.sql.Connection;

public class test {
    public static void main(String[] args) {
//        File f=new File("jdbc.properties");
//        try {
//            f.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            Connection connection = DBUtils.getConnection();
//            System.out.println(connection);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String sql = "insert into test value(?,?,?,?)";
        String[] parameter={"jack","男","19","美国"};
        int count = DBUtils.setUpdate(sql,parameter);
        System.out.println("修改数量："+count);
    }
}
