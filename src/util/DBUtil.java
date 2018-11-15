package util;
import java.sql.*;

/**
 * JDBC 的工具类
 *
 * 其中包含: 获取数据库连接, 关闭数据库资源等方法.
 */
public class DBUtil {

    public static Connection getConnection() throws Exception {
//        Properties properties = new Properties();
//        InputStream inStream = DBUtil.class.getClassLoader()
//                .getResourceAsStream("jdbc.properties");
//        properties.load(inStream);

        // 1. 准备获取连接的 4 个字符串: user, password, url, jdbcDriver
//        String user = properties.getProperty("user");
//        String password = properties.getProperty("password");
//        String url= properties.getProperty("url");
//        String jdbcDriver= properties.getProperty("jdbcDriver");
        String url = "jdbc:mysql://localhost:3306/fastcar?useUnicode=true&characterEncoding=UTF-8";
        //连接的数据库时使用的用户名
        String username = "root";
        //连接的数据库时使用的密码
        String password = "";
        // 2. 加载驱动: Class.forName(driverClass)
        Class.forName("com.mysql.jdbc.Driver");

        // 3.获取数据库连接
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static void releaseDB(ResultSet resultSet, Statement statement,
                                 Connection connection) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}