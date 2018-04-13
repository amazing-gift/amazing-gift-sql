package com.amazing_gift.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetHandler {

	public int handle(ResultSet resultSet) throws SQLException;

}
