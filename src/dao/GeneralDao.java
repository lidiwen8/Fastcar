package dao;

import com.sun.rowset.CachedRowSetImpl;
import util.JDBCUtil;
import util.RowMapper;
import util.RowMapperList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GeneralDao {
	
	// 更新操作，添加、删除，修改
	public static int update(String sql, Object[] values) {

		Connection conn = JDBCUtil.getConn();
		PreparedStatement preStmt = null;
		int successCount = 0;
		try {
			preStmt = conn.prepareStatement(sql);
			for (int i = 0; i < values.length; i++)
				preStmt.setObject(i + 1, values[i]);
			preStmt.executeUpdate();
			successCount = preStmt.getUpdateCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConn(null,preStmt, conn);
		}
		return successCount;
	}
	
	// 查询操作，返回一个离线结果集
	public static CachedRowSetImpl queryCached(String sql, Object[] values) {
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		CachedRowSetImpl rowSet = null;
		try {
			preStmt= conn.prepareStatement(sql);
			for(int i =0;i<values.length;i++)
				preStmt.setObject(i+1, values[i]);
			rs = preStmt.executeQuery();
			rowSet = new CachedRowSetImpl();
			rowSet.populate(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCUtil.closeConn(rs,preStmt,conn);
		}
		return rowSet;
	}
	

	// 查询，返回一个对象
	public static Object query(String sql, Object[] values, RowMapper rowMapper) {
		Object object = null;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			preStmt= conn.prepareStatement(sql);
			for(int i =0;i<values.length;i++)
				preStmt.setObject(i+1, values[i]);
			rs = preStmt.executeQuery();
			object = rowMapper.rowMapping(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCUtil.closeConn(rs,preStmt,conn);
		}
		return object;
	}

	// 查询，返回一组对象
	public static List query(String sql, Object[] values, RowMapperList rowMapperList) {
		List list = null;
		Connection conn = JDBCUtil.getConn();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			preStmt= conn.prepareStatement(sql);
			for(int i =0;i<values.length;i++)
				preStmt.setObject(i+1, values[i]);
			rs = preStmt.executeQuery();
			list = rowMapperList.rowMapping(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCUtil.closeConn(rs,preStmt,conn);
		}
		if (list == null || list.isEmpty() || list.size() == 0) {
			return null;	
		}
		return list;
	}
	
}

