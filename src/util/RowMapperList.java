package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RowMapperList {

	public List rowMapping(ResultSet rs) throws SQLException;
}
