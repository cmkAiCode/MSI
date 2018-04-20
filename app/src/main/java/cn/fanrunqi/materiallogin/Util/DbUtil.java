package cn.fanrunqi.materiallogin.Util;
import java.sql.*;

/**
 * 连接数据库的工具类
 */
public class DbUtil {
    /**
     * 连接数据库 返回连接的Connection对象
     * @return
     */
    public static Connection getConnection(){
        Connection con = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String user = "yl";
            String pass = "1997";
            String url = "jdbc:sqlserver://localhost:1433;DataBaseName=CRM";
            con = DriverManager.getConnection(url,user,pass);
            System.out.println("数据库连接成功！");
        }catch(ClassNotFoundException e){}
        catch(SQLException e){
            System.out.println("数据库连接失败！");
        }
        return con;
    }

    /**
     * 用来关闭数据库的连接
     * @param con 获取的数据库连接
     * @param s    Statement变量
     * @param ps PreparedStatemet变量
     * @param rs ResultSet结果集
     */
    public static void closeAll(Connection con,Statement s,PreparedStatement ps,ResultSet rs){
        try{
            if (rs != null) rs.close();
            if (s != null) s.close();
            if(ps != null) ps.close();
            if (con != null) con.close();
        }catch(Exception e){
            System.out.println("关闭失败");
        }
    }

    //测试用
//    public static void main(String args[]){
//        Connection con = DbUtil.getConnection();
//        try {
//            Statement s = con.createStatement();
//            ResultSet rss = s.executeQuery("select * from basic_info");
//            while(rss.next()){
//                System.out.println(rss.getString(1)+"  "+rss.getString(2)+"  "+rss.getString(3));
//            }
//        }catch(Exception e){}
//    }
}
