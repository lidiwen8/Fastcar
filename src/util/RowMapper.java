package util;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper {
	public Object rowMapping(ResultSet rs) throws SQLException;
	
}
