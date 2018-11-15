package util;

import java.sql.*;

public class JDBCUtil {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("JDBC driver error !");
		}
	}

	// 获取连接
	public static Connection getConn() {
		Connection conn = null;
		try {
			String url = "jdbc:mysql://127.0.0.1:3306/fastcar?useUnicode=true&characterEncoding=UTF-8";
			String username = "root";
			String password = "";
			 conn = DriverManager.getConnection(url, username, password);
			System.out.println("JDBC connection successful !");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("JDBC connection error !");
		}
		return conn;
	}
	
	// 关闭连接
	public static void closeConn(ResultSet rs,Statement st,Connection conn) {
		try {
			if(rs!=null) 
				rs.close();
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			System.out.println("JDBC close error");
		}
	}
	
	/*
	public static void main(String [] args) {
		Connection conn = JDBCUtil.getConn();
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	*/
}
